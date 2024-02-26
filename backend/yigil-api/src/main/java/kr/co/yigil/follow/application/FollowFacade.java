package kr.co.yigil.follow.application;

import kr.co.yigil.follow.domain.FollowService;
import kr.co.yigil.notification.application.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowFacade {
    private final FollowService followService;
    private final NotificationService notificationService;

    public void follow(Long followerId, Long followingId) {
        followService.follow(followerId, followingId);
        notificationService.sendFollowNotification(followerId, followingId);
    }

    public void unfollow(Long unfollowerId, Long unfollowingId) {
        followService.unfollow(unfollowerId, unfollowingId);
        notificationService.sendUnfollowNotification(unfollowerId, unfollowingId);
    }
}
