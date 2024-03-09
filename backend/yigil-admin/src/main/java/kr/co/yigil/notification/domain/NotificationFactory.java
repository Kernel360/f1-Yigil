package kr.co.yigil.notification.domain;

import kr.co.yigil.admin.domain.Admin;
import kr.co.yigil.member.Member;
import org.springframework.stereotype.Component;

@Component
public class NotificationFactory {
    public Notification createNotification(NotificationType notificationType, Admin admin, Member receiver) {
        String message = notificationType.composeMessage(admin.getNickname(), receiver.getNickname());
        return new Notification(receiver, message);
    }

}
