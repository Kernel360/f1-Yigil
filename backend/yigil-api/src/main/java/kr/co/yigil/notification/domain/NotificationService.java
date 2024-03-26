package kr.co.yigil.notification.domain;

import org.springframework.data.domain.PageRequest;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

public interface NotificationService {

    SseEmitter createEmitter(Long memberId);

    void sendNotification(NotificationType notificationType, Long senderId, Long receiverId);

    NotificationInfo.NotificationsSlice getNotificationSlice(Long memberId, PageRequest pageRequest);

    void readNotification(Long memberId, List<Long> notificationId);
}
