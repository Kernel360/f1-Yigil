package kr.co.yigil.follow.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.domain.MemberReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

public class FollowServiceImplTest {

    @Mock
    private FollowReader followReader;

    @Mock
    private MemberReader memberReader;

    @Mock
    private FollowStore followStore;

    @Mock
    private FollowCacheStore followCacheStore;

    @InjectMocks
    private FollowServiceImpl followService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("유효한 파라미터로 follow 메서드가 잘 호출되는지")
    @Test
    void whenFollow_valid_thenCallsMethod() {
        Long followerId = 1L;
        Long followingId = 2L;

        when(followReader.isFollowing(anyLong(), anyLong())).thenReturn(false);

        Member mockMember = mock(Member.class);
        when(memberReader.getMember(anyLong())).thenReturn(mockMember);

        followService.follow(followerId, followingId);

        verify(followStore, times(1)).store(any(Member.class), any(Member.class));
        verify(followCacheStore, times(1)).incrementFollowersCount(followingId);
        verify(followCacheStore, times(1)).incrementFollowingsCount(followerId);
    }

    @DisplayName("이미 팔로우 중인 회원이 follow를 호출 시  예외가 잘 발생되는지")
    @Test
    void whenFollow_alreadyFollowing_thenThrowsException() {
        Long followerId = 1L;
        Long followingId = 2L;

        when(followReader.isFollowing(anyLong(), anyLong())).thenReturn(true);

        assertThrows(
                BadRequestException.class, () -> followService.follow(followerId, followingId));

    }
    @DisplayName("자기 자신을 팔로우하려 할 때 예외가 잘 발생되는지")
    @Test
    void whenFollow_self_thenThrowsException() {
        Long followerId = 1L;
        Long followingId = 1L;

        assertThrows(
                BadRequestException.class, () -> followService.follow(followerId, followingId));
    }

    @DisplayName("유효한 파라미터로 unfollow 메서드가 잘 호출되는지")
    @Test
    void whenUnfollow_valid_thenCallsMethod() {
        Long unfollowerId = 1L;
        Long unfollowingId = 2L;

        when(followReader.isFollowing(anyLong(), anyLong())).thenReturn(true);

        Member mockMember = mock(Member.class);
        when(memberReader.getMember(anyLong())).thenReturn(mockMember);

        followService.unfollow(unfollowerId, unfollowingId);

        verify(followStore, times(1)).remove(any(Member.class), any(Member.class));
        verify(followCacheStore, times(1)).decrementFollowersCount(unfollowingId);
        verify(followCacheStore, times(1)).decrementFollowingsCount(unfollowerId);
    }

    @DisplayName("이미 언팔로우 한 회원이 unfollow를 호출할 경우 예외가 잘 발생되는지")
    @Test
    void whenUnfollow_notFollowing_thenThrowsException() {
        Long unfollowerId = 1L;
        Long unfollowingId = 2L;

        when(followReader.isFollowing(anyLong(), anyLong())).thenReturn(false);

        assertThrows(
                BadRequestException.class, () -> followService.unfollow(unfollowerId, unfollowingId));
    }

    @DisplayName("자기 자신을 언팔로우하려 할 때 예외가 잘 발생되는지")
    @Test
    void whenUnfollow_self_thenThrowsException() {
        Long unfollowerId = 1L;
        Long unfollowingId = 1L;

        assertThrows(
                BadRequestException.class, () -> followService.unfollow(unfollowerId, unfollowingId));
    }


    @DisplayName("getFollowerList 를 호출했을 때 팔로워 리스트 조회가 잘 되는지 확인")
    @Test
    void WhenGetFollowerList_ThenShouldReturnFollowerResponse() {
        Long memberId = 1L;
        PageRequest pageable = PageRequest.of(0, 10);

        // 필수 멤버 필드:   id, nickname, profileImageUrl
        Member mockMember = new Member(memberId, null, null, "nickname", "profileImageUrl", null);
        Follow mockFollow = mock(Follow.class);

        Slice<Follow> mockFollowList = new SliceImpl<>(List.of(mockFollow));

        when(followReader.getFollowerSlice(anyLong(), any())).thenReturn(mockFollowList);
        when(mockFollow.getFollower()).thenReturn(mockMember);

        var result = followService.getFollowerList(memberId, pageable);

        assertThat(result).isNotNull().isInstanceOf(FollowInfo.FollowersResponse.class);
    }

    @DisplayName("getFollowingList 를 호출했을 때 팔로잉 리스트 조회가 잘 되는지 확인")
    @Test
    void WhenGetFollowingList_ThenShouldReturnFollowingResponse() {
        Long memberId = 1L;
        PageRequest pageable = PageRequest.of(0, 10);

        // 필수 멤버 필드:   id, nickname, profileImageUrl
        Member mockMember = new Member(memberId, null, null, "nickname", "profileImageUrl", null);
        Follow mockFollow = mock(Follow.class);

        Slice<Follow> mockFollowList = new SliceImpl<>(List.of(mockFollow));

        when(followReader.getFollowingSlice(anyLong(), any())).thenReturn(mockFollowList);
        when(mockFollow.getFollowing()).thenReturn(mockMember);

        var result = followService.getFollowingList(memberId, pageable);

        assertThat(result).isNotNull().isInstanceOf(FollowInfo.FollowingsResponse.class);
    }

}