package kr.co.yigil.notification.application;

import kr.co.yigil.member.Member;
import kr.co.yigil.member.domain.MemberReader;
import kr.co.yigil.notification.domain.Notification;
import kr.co.yigil.notification.domain.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final MemberReader memberReader;
    private final Sinks.Many<Notification> sink = Sinks.many().multicast().onBackpressureBuffer();
    private final NotificationRepository notificationRepository;

    public void sendNotification(Notification notification) {
        notificationRepository.save(notification);
        sendRealTimeNotification(notification);
    }

    private void sendRealTimeNotification(Notification notification) {
        sink.tryEmitNext(notification);
    }

    public Flux<ServerSentEvent<Notification>> getNotificationStream(Long memberId) {
        return sink.asFlux()
                .filter(notification -> notification.getMember().getId().equals(memberId))
                .map(notification -> ServerSentEvent.<Notification>builder()
                        .data(notification)
                        .build());
    }

    public void sendFollowNotification(Long followerId, Long followingId) {
        Member follower = memberReader.getMember(followerId);
        Member following = memberReader.getMember(followingId);
        String message = follower.getNickname() + "님이 팔로우 하였습니다.";
        Notification notify = new Notification(following, message);
        sendNotification(notify);
    }

    public void sendUnfollowNotification(Long unfollowerId, Long unfollowingId) {
        Member unfollower = memberReader.getMember(unfollowerId);
        Member unfollowing = memberReader.getMember(unfollowingId);
        String message = unfollower.getNickname() + "님이 언팔로우 하였습니다.";
        Notification notify = new Notification(unfollowing, message);
        sendNotification(notify);
    }
}
