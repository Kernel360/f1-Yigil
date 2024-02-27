package kr.co.yigil.follow.application;

import kr.co.yigil.follow.domain.FollowService;
import kr.co.yigil.notification.domain.NotificationService;
import kr.co.yigil.notification.domain.NotificationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class FollowFacadeTest {

    @Mock
    private FollowService followService;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private FollowFacade followFacade;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("follow 메서드가 FollowService와 NotificationService의 메서드를 잘 호출하는지")
    @Test
    void whenFollow_thenCallsMethods() {
        Long followerId = 1L;
        Long followingId = 2L;

        followFacade.follow(followerId, followingId);

        verify(followService, times(1)).follow(followerId, followingId);
        verify(notificationService, times(1)).sendNotification(NotificationType.FOLLOW, followerId, followingId);
    }


    @DisplayName("unfollow 메서드가 FollowService와 NotificationService의 메서드를 잘 호출하는지")
    @Test
    void whenUnfollow_thenCallsMethods() {
        Long unfollowerId = 1L;
        Long unfollowingId = 2L;

        followFacade.unfollow(unfollowerId, unfollowingId);

        verify(followService, times(1)).unfollow(unfollowerId, unfollowingId);
        verify(notificationService, times(1)).sendNotification(NotificationType.UNFOLLOW, unfollowerId, unfollowingId);
    }
}