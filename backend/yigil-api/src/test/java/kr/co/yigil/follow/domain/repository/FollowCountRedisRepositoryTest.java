package kr.co.yigil.follow.domain.repository;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

public class FollowCountRedisRepositoryTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private HashOperations<String, Object, Object> hashOperations;

    private FollowCountRedisRepository followCountRedisRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
        followCountRedisRepository = new FollowCountRedisRepository(redisTemplate);
    }

    @DisplayName("팔로워 수가 잘 증가하는지")
    @Test
    void incrementFollowersCountTest() {
        Long memberId = 1L;
        followCountRedisRepository.incrementFollowersCount(memberId);
        verify(hashOperations, times(1)).increment("followCount", memberId + ":followerCount", 1);
    }

    @DisplayName("팔로워 수가 잘 감소하는지")
    @Test
    void decrementFollowersCountTest() {
        Long memberId = 1L;
        followCountRedisRepository.decrementFollowersCount(memberId);
        verify(hashOperations, times(1)).increment("followCount", memberId + ":followerCount", -1);
    }

    @DisplayName("팔로잉 수가 잘 증가하는지")
    @Test
    void incrementFollowingsCountTest() {
        Long memberId = 1L;
        followCountRedisRepository.incrementFollowingsCount(memberId);
        verify(hashOperations, times(1)).increment("followCount", memberId + ":followingCount", 1);
    }

    @DisplayName("팔로잉 수가 잘 감소하는지")
    @Test
    void decrementFollowingsCountTest() {
        Long memberId = 1L;
        followCountRedisRepository.decrementFollowingsCount(memberId);
        verify(hashOperations, times(1)).increment("followCount", memberId + ":followingCount", -1);
    }

}
