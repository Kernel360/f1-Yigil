package kr.co.yigil.notification.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.domain.MemberReader;
import kr.co.yigil.notification.domain.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationSenderImpl implements NotificationSender {
    private final MemberReader memberReader;
    private final NotificationStore notificationStore;
    private final NotificationFactory notificationFactory;

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final Map<Long, SseEmitter> userEmitters = new ConcurrentHashMap<>();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    public SseEmitter createEmitter(Long memberId) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        userEmitters.put(memberId, emitter);

        emitter.onCompletion(() -> userEmitters.remove(memberId));
        emitter.onTimeout(() -> userEmitters.remove(memberId));
        emitter.onError((e) -> userEmitters.remove(memberId));

        return emitter;
    }

    @Override
    @Transactional
    public void sendNotification(NotificationType notificationType, Long senderId,
            Long receiverId) {
        Member sender = memberReader.getMember(senderId);
        Member receiver = memberReader.getMember(receiverId);
        Notification notification = notificationFactory.createNotification(notificationType, sender, receiver);
        notificationStore.store(notification);
        publishNotification(notification);
    }

    private void publishNotification(Notification notification) {
        redisTemplate.convertAndSend("notificationTopic", notification);
    }

    private void sendRealTimeNotification(Notification notification) {
        SseEmitter emitter = userEmitters.get(notification.getReceiver().getId());
        if (emitter != null) {
            executor.execute(() -> {
                try {
                    emitter.send(SseEmitter.event().name("notification").data(notification.getMessage()));
                } catch (Exception e) {
                    log.error("Failed to send notification", e);
                }
            });
        }
    }

    @Bean
    RedisMessageListenerContainer redisContainer(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener((message, pattern) -> {
            String body = redisTemplate.getStringSerializer().deserialize(message.getBody());
            Notification notification = null;
            try {
                notification = objectMapper.readValue(body, Notification.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            assert notification != null;
            sendRealTimeNotification(notification);
        }, new PatternTopic("notificationTopic"));
        return container;
    }

}
