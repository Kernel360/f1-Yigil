package kr.co.yigil.notification.domain;

public interface NotificationService {
    void saveNotification(NotificationType notificationType, Long senderId, Long receiverId);

}
