package kr.co.yigil.follow.application;

import kr.co.yigil.follow.domain.FollowService;
import kr.co.yigil.notification.domain.NotificationSender;
import kr.co.yigil.notification.domain.NotificationService;
import kr.co.yigil.notification.domain.NotificationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowFacade {

    private final FollowService followService;
    private final NotificationService notificationService;

    public void follow(Long followerId, Long followingId) {
        followService.follow(followerId, followingId);
        notificationService.sendNotification(NotificationType.FOLLOW, followerId, followingId);
    }

    public void unfollow(Long unfollowerId, Long unfollowingId) {
        followService.unfollow(unfollowerId, unfollowingId);
        notificationService.sendNotification(NotificationType.UNFOLLOW, unfollowerId, unfollowingId);

    }
}
