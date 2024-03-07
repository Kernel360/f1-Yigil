package kr.co.yigil.notification.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{
    private final NotificationReader notificationReader;
    private final NotificationSender notificationSender;
//    @Transactional(readOnly = true)
//    @Override
//    public Flux<ServerSentEvent<Notification>> getNotificationStream(Long memberId) {
//        return notificationReader.getNotificationStream(memberId);
//    }

    @Transactional
    @Override
    public void sendNotification(NotificationType notificationType, Long senderId, Long receiverId) {
        notificationSender.sendNotification(notificationType, senderId, receiverId);
    }

//    @Transactional(readOnly = true)
//    @Override
//    public Slice<Notification> getNotificationSlice(Long memberId, PageRequest pageRequest) {
//        return notificationReader.getNotificationSlice(memberId, pageRequest);
//    }

}
