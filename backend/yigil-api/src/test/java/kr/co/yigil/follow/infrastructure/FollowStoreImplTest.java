package kr.co.yigil.follow.infrastructure;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import kr.co.yigil.follow.domain.Follow;
import kr.co.yigil.follow.domain.repository.FollowRepository;
import kr.co.yigil.member.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class FollowStoreImplTest {

    @Mock
    private FollowRepository followRepository;

    @InjectMocks
    private FollowStoreImpl followStore;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenFollow_thenSaveIsCalled() {
        Member follower = new Member("kiit0901@gmail.com", "123456", "stone", "profile.jpg", "kakao");
        Member following = new Member("kiit09sdf01@gmail.com", "123456", "stone", "profile.jpg", "kakao");

        followStore.follow(follower, following);

        verify(followRepository, times(1)).save(new Follow(follower, following));
    }

    @Test
    void whenUnfollow_thenDeleteByFollowerAndFollowingIsCalled() {
        Member unfollower =  new Member("kiit0901@gmail.com", "123456", "stone", "profile.jpg", "kakao");
        Member unfollowing = new Member("kiit09sdf01@gmail.com", "123456", "stone", "profile.jpg", "kakao");

        followStore.unfollow(unfollower, unfollowing);

        verify(followRepository, times(1)).deleteByFollowerAndFollowing(unfollower, unfollowing);
    }
}