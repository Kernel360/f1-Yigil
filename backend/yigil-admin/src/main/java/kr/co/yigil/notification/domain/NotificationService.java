package kr.co.yigil.notification.domain;

public interface NotificationService {

//    Flux<ServerSentEvent<Notification>> getNotificationStream(Long memberId);

    void sendNotification(NotificationType notificationType, Long senderId, Long receiverId);

//    Slice<Notification> getNotificationSlice(Long memberId, PageRequest pageRequest);
}
