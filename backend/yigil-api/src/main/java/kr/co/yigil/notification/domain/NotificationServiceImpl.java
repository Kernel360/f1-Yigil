package kr.co.yigil.notification.domain;

import kr.co.yigil.member.Member;
import kr.co.yigil.member.domain.MemberReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import kr.co.yigil.notification.domain.util.NotificationCreator;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{
    public final NotificationReader notificationReader;
    public final NotificationStore notificationStore;
    public final MemberReader memberReader;
    private final Sinks.Many<Notification> sink = Sinks.many().multicast().onBackpressureBuffer();

    @Override
    public Flux<ServerSentEvent<Notification>> getNotificationStream(Long memberId) {
        return notificationReader.getNotificationStream(memberId);
    }

    @Override
    public void sendNotification(NotificationCreator notificationCreator, Long senderId,
            Long receiverId) {
        Member sender = memberReader.getMember(senderId);
        Member receiver = memberReader.getMember(receiverId);
        Notification notification = notificationCreator.createNotification(sender, receiver);
        notificationStore.store(notification);
        sendRealTimeNotification(notification);
    }

    @Override
    public Slice<Notification> getNotificationSlice(Long memberId, PageRequest pageRequest) {
        return notificationReader.getNotificationSlice(memberId, pageRequest);
    }

    private void sendRealTimeNotification(Notification notification) {
        sink.tryEmitNext(notification);
    }

}
