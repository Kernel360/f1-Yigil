package kr.co.yigil.notification.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{
    private final NotificationReader notificationReader;
    private final NotificationSender notificationSender;
    @Transactional(readOnly = true)
    @Override
    public Flux<ServerSentEvent<Notification>> getNotificationStream(Long memberId) {
        return notificationReader.getNotificationStream(memberId);
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

    @Transactional
    @Override
    public void readNotification(Long memberId, Long notificationId) {
        Notification notification = notificationReader.getNotification(memberId, notificationId);
        notification.read();
    }
}
