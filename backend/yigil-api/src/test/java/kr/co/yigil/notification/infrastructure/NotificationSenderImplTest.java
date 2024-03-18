package kr.co.yigil.notification.infrastructure;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import kr.co.yigil.member.Member;
import kr.co.yigil.member.SocialLoginType;
import kr.co.yigil.member.domain.MemberReader;
import kr.co.yigil.notification.domain.Notification;
import kr.co.yigil.notification.domain.NotificationFactory;
import kr.co.yigil.notification.domain.NotificationSender;
import kr.co.yigil.notification.domain.NotificationStore;
import kr.co.yigil.notification.domain.NotificationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitResult;
import reactor.test.StepVerifier;

class NotificationSenderImplTest {

    @Mock
    private MemberReader memberReader;

    @Mock
    private NotificationStore notificationStore;

    @Mock
    private NotificationFactory notificationFactory;

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @InjectMocks
    private NotificationSenderImpl notificationSender;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("createEmitter 메서드가 emitter를 잘 생성하고 반환하는지")
    @Test
    void createEmitter_ShouldReturnEmitter() {
        Long memberId = 1L;
        SseEmitter emitter = notificationSender.createEmitter(memberId);
        assertNotNull(emitter);
    }

    @DisplayName("sendNotification 메서드가 알림을 잘 생성하는지")
    @Test
    void sendNotification_publishesNotification() {
        Long senderId = 1L;
        Long receiverId = 2L;
        Member sender = mock(Member.class);
        Member receiver = mock(Member.class);
        Notification notification = mock(Notification.class);

        when(memberReader.getMember(senderId)).thenReturn(sender);
        when(memberReader.getMember(receiverId)).thenReturn(receiver);
        when(notificationFactory.createNotification(any(NotificationType.class), any(Member.class), any(Member.class)))
                .thenReturn(notification);

        notificationSender.sendNotification(NotificationType.FOLLOW, senderId, receiverId);

        verify(notificationFactory).createNotification(any(NotificationType.class), any(Member.class), any(Member.class));
        verify(notificationStore).store(notification);
        verify(redisTemplate).convertAndSend("notificationTopic", notification);
    }
}