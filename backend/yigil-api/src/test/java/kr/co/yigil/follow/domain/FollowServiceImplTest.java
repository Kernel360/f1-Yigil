package kr.co.yigil.follow.domain;

import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.domain.MemberReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    private FollowServiceImpl followServiceImpl;

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

        followServiceImpl.follow(followerId, followingId);

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
                BadRequestException.class, () -> followServiceImpl.follow(followerId, followingId));

    }
    @DisplayName("자기 자신을 팔로우하려 할 때 예외가 잘 발생되는지")
    @Test
    void whenFollow_self_thenThrowsException() {
        Long followerId = 1L;
        Long followingId = 1L;

        assertThrows(
                BadRequestException.class, () -> followServiceImpl.follow(followerId, followingId));
    }

    @DisplayName("유효한 파라미터로 unfollow 메서드가 잘 호출되는지")
    @Test
    void whenUnfollow_valid_thenCallsMethod() {
        Long unfollowerId = 1L;
        Long unfollowingId = 2L;

        when(followReader.isFollowing(anyLong(), anyLong())).thenReturn(true);

        Member mockMember = mock(Member.class);
        when(memberReader.getMember(anyLong())).thenReturn(mockMember);

        followServiceImpl.unfollow(unfollowerId, unfollowingId);

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
                BadRequestException.class, () -> followServiceImpl.unfollow(unfollowerId, unfollowingId));
    }

    @DisplayName("자기 자신을 언팔로우하려 할 때 예외가 잘 발생되는지")
    @Test
    void whenUnfollow_self_thenThrowsException() {
        Long unfollowerId = 1L;
        Long unfollowingId = 1L;

        assertThrows(
                BadRequestException.class, () -> followServiceImpl.unfollow(unfollowerId, unfollowingId));
    }

}