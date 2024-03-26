package kr.co.yigil.notification.domain;

import lombok.Data;
import org.springframework.data.domain.Slice;

import java.util.List;

public class NotificationInfo {

    @Data
    public static class NotificationsSlice {

        List<NotificationsUnitInfo> notifications;
        boolean hasNext;

        public NotificationsSlice(Slice<Notification> notifications) {
            this.notifications = notifications.getContent().stream().map(NotificationsUnitInfo::new).toList();
            this.hasNext = notifications.hasNext();
        }
    }


    @Data
    public static class NotificationsUnitInfo {
        private Long notificationId;
        private String message;
        private String createDate;
        private Long senderId = -1L;
        private String senderProfileImageUrl = "";
        private boolean read;

        public NotificationsUnitInfo(Notification notification) {
            this.notificationId = notification.getId();
            this.message = notification.getMessage();
            this.createDate = notification.getCreatedAt().toString();
            this.read = notification.isRead();
            if(notification.getSender() != null) {
                this.senderId = notification.getSender().getId();
                this.senderProfileImageUrl = notification.getSender().getProfileImageUrl();
            }
        }
    }
}
