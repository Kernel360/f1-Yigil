package kr.co.yigil.notification.domain;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationSender {

    SseEmitter createEmitter(Long memberId);

    void sendNotification(NotificationType notificationType, Long senderId,
            Long receiverId);
}
