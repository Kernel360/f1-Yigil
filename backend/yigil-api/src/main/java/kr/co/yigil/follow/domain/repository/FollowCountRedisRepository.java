package kr.co.yigil.follow.domain.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FollowCountRedisRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    public void incrementFollowersCount(Long memberId) {
        redisTemplate.opsForHash().increment("followCount", buildHashKey(memberId, "followerCount"), 1);
    }

    public void decrementFollowersCount(Long memberId) {
        redisTemplate.opsForHash().increment("followCount", buildHashKey(memberId, "followerCount"), -1);
    }

    public void incrementFollowingsCount(Long memberId) {
        redisTemplate.opsForHash().increment("followCount", buildHashKey(memberId, "followingCount"), 1);
    }

    public void decrementFollowingsCount(Long memberId) {
        redisTemplate.opsForHash().increment("followCount", buildHashKey(memberId, "followingCount"), -1);
    }

    private String buildHashKey(Long memberId, String fieldName) {
        return memberId + ":" + fieldName;
    }
}
