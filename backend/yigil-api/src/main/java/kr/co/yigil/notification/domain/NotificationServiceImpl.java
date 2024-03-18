package kr.co.yigil.notification.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{
    private final NotificationReader notificationReader;
    private final NotificationSender notificationSender;


    @Override
    public SseEmitter createEmitter(Long memberId) {
        return notificationSender.createEmitter(memberId);
    }

    @Transactional
    @Override
    public void sendNotification(NotificationType notificationType, Long senderId, Long receiverId) {
        notificationSender.sendNotification(notificationType, senderId, receiverId);
    }

    @Transactional(readOnly = true)
    @Override
    public Slice<Notification> getNotificationSlice(Long memberId, PageRequest pageRequest) {
        return notificationReader.getNotificationSlice(memberId, pageRequest);
    }

}
