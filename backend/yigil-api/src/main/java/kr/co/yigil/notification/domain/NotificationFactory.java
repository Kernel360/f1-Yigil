package kr.co.yigil.notification.domain;

import kr.co.yigil.member.Member;
import kr.co.yigil.notification.domain.Notification;
import kr.co.yigil.notification.domain.NotificationType;
import org.springframework.stereotype.Component;

@Component
public class NotificationFactory {
    public Notification createNotification(NotificationType notificationType, Member sender, Member receiver) {
        String message = notificationType.composeMessage(sender.getNickname(), receiver.getNickname());
        return new Notification(receiver, message);
    }

}
