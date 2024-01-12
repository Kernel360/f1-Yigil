package kr.co.yigil.follow.application;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import kr.co.yigil.follow.domain.Follow;
import kr.co.yigil.follow.domain.repository.FollowCountRedisRepository;
import kr.co.yigil.follow.domain.repository.FollowRepository;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.member.domain.Member;
import kr.co.yigil.member.domain.SocialLoginType;
import kr.co.yigil.member.domain.repository.MemberRepository;
import kr.co.yigil.notification.application.NotificationService;
import kr.co.yigil.notification.domain.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class FollowServiceTest {

    @Mock
    private FollowRepository followRepository;

    @Mock
    private FollowCountRedisRepository followCountRedisRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private NotificationService notificationService;

    @Mock
    private FollowRedisIntegrityService followRedisIntegrityService;

    @InjectMocks
    private FollowService followService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("follow 메서드가 팔로우를 잘 저장하고 알림을 잘 보내는지")
    @Test
    void whenFollow_ShouldSaveFollowAndSendNotification() {
        Long followerId = 1L;
        Long followingId = 1L;
        Member follower = new Member(followerId, "email", "12345678", "follower", "image.jpg", SocialLoginType.KAKAO);
        Member following = new Member(followingId, "email2", "87654321", "following", "profile.png", SocialLoginType.KAKAO);

        when(memberRepository.findById(followerId)).thenReturn(Optional.of(follower));
        when(memberRepository.findById(followingId)).thenReturn(Optional.of(following));

        followService.follow(followerId, followingId);

        verify(followRepository, times(1)).save(any(Follow.class));
        verify(followCountRedisRepository, times(1)).incrementFollowersCount(followingId);
        verify(followCountRedisRepository, times(1)).incrementFollowingsCount(followerId);
        verify(notificationService, times(1)).sendNotification(any(Notification.class));
    }

    @DisplayName("follow 메서드가 존재하지 않는 사용자에 대한 요청을 보냈을 때 예외가 잘 발생하는지")
    @Test
    void whenFollowWithInvalidMemberInfo_ShouldThrowException() {
        Long nonExistingId = 3L;

        when(memberRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> followService.follow(1L, nonExistingId));
    }

    @DisplayName("unfollow 메서드가 팔로우를 잘 삭제하는지")
    @Test
    void whenUnfollow_ShouldDeleteFollow() {
        Long followerId = 1L;
        Long followingId = 2L;
        Member unfollower = new Member(followerId, "email", "12345678", "follower", "image.jpg", SocialLoginType.KAKAO);
        Member unfollowing = new Member(followingId, "email2", "87654321", "following", "profile.png", SocialLoginType.KAKAO);

        when(memberRepository.findById(followerId)).thenReturn(Optional.of(unfollower));
        when(memberRepository.findById(followingId)).thenReturn(Optional.of(unfollowing));

        followService.unfollow(followerId, followingId);

        verify(followRepository, times(1)).deleteByFollowerAndFollowing(unfollower, unfollowing);
        verify(followCountRedisRepository, times(1)).decrementFollowersCount(followingId);
        verify(followCountRedisRepository, times(1)).decrementFollowingsCount(followerId);
    }

}
