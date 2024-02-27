package kr.co.yigil.notification.domain;

public interface NotificationSender {

    void sendNotification(NotificationType notificationType, Long senderId,
            Long receiverId);
}
