package kr.co.yigil.notification.infrastructure;

import kr.co.yigil.notification.domain.NotificationFactory;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.domain.MemberReader;
import kr.co.yigil.notification.domain.Notification;
import kr.co.yigil.notification.domain.NotificationSender;
import kr.co.yigil.notification.domain.NotificationStore;
import kr.co.yigil.notification.domain.NotificationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Sinks;
@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationSenderImpl implements NotificationSender {
    private final MemberReader memberReader;
    private final Sinks.Many<Notification> sink = Sinks.many().multicast().onBackpressureBuffer();
    private final NotificationStore notificationStore;
    private final NotificationFactory notificationFactory;
    @Override
    public void sendNotification(NotificationType notificationType, Long senderId,
            Long receiverId) {
        Member sender = memberReader.getMember(senderId);
        Member receiver = memberReader.getMember(receiverId);
        Notification notification = notificationFactory.createNotification(notificationType, sender, receiver);
        notificationStore.store(notification);
        sendRealTimeNotification(notification);
    }
    private void sendRealTimeNotification(Notification notification) {
        log.error(notification.getMember().getNickname() + "님에게 알림을 전송하였습니다.");
        sink.tryEmitNext(notification);
    }

}
