package kr.co.yigil.follow.application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import kr.co.yigil.follow.domain.FollowInfo;
import kr.co.yigil.follow.domain.FollowService;
import kr.co.yigil.notification.domain.NotificationService;
import kr.co.yigil.notification.domain.NotificationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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

        @DisplayName("getFollowerList 메서드가 유효한 요청이 들어왔을 때 MemberService의 getFollowerList 메서드를 잘 호출하는지")
    @Test
    void WhenGetFollowerList_ThenShouldReturnFollowerResponse() {
        Long memberId = 1L;
        PageRequest pageable = mock(PageRequest.class);

        FollowInfo.FollowersResponse mockFollowerResponse = new FollowInfo.FollowersResponse(
            Collections.emptyList(), false);
        when(followService.getFollowerList(memberId, pageable)).thenReturn(mockFollowerResponse);

        var result = followFacade.getFollowerList(memberId, pageable);

        assertThat(result).isNotNull()
            .isInstanceOf(FollowInfo.FollowersResponse.class)
            .usingRecursiveComparison().isEqualTo(mockFollowerResponse);
    }

    @DisplayName("getFollowerList 메서드가 유효한 요청이 들어왔을 때 MemberService의 getFollowerList 메서드를 잘 호출하는지")
    @Test
    void WhenGetFollowingList_ThenShouldReturnFollowingResponse() {
        Long memberId = 1L;
        PageRequest pageable = mock(PageRequest.class);

        FollowInfo.FollowingsResponse mockFollowingResponse = new FollowInfo.FollowingsResponse(
            Collections.emptyList(), false);
        when(followService.getFollowingList(anyLong(), any(Pageable.class))).thenReturn(mockFollowingResponse);

        var result = followFacade.getFollowingList(memberId, pageable);

        assertThat(result).isNotNull()
            .isInstanceOf(FollowInfo.FollowingsResponse.class)
            .usingRecursiveComparison().isEqualTo(mockFollowingResponse);
    }
}