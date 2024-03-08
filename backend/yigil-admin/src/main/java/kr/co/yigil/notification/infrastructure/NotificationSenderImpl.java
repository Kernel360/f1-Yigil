package kr.co.yigil.notification.infrastructure;

import kr.co.yigil.admin.domain.Admin;
import kr.co.yigil.admin.domain.admin.AdminReader;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.domain.MemberReader;
import kr.co.yigil.notification.domain.Notification;
import kr.co.yigil.notification.domain.NotificationFactory;
import kr.co.yigil.notification.domain.NotificationSender;
import kr.co.yigil.notification.domain.NotificationStore;
import kr.co.yigil.notification.domain.NotificationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Sinks;
@Component
@RequiredArgsConstructor
public class NotificationSenderImpl implements NotificationSender {
    private final MemberReader memberReader;
    private final AdminReader adminReader;
    private final Sinks.Many<Notification> sink = Sinks.many().multicast().onBackpressureBuffer();
    private final NotificationStore notificationStore;
    private final NotificationFactory notificationFactory;
    @Override
    public void sendNotification(NotificationType notificationType, Long adminId,
            Long receiverId) {
        Admin sender = adminReader.getAdmin(adminId);
        Member receiver = memberReader.getMember(receiverId);
        Notification notification = notificationFactory.createNotification(notificationType, sender, receiver);
        notificationStore.store(notification);
        sendRealTimeNotification(notification);
    }
    private void sendRealTimeNotification(Notification notification) {
        sink.tryEmitNext(notification);
    }
}
