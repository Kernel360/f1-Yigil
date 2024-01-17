package kr.co.yigil.like.domain.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LikeCountRedisRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    public void incrementLikeCount(Long postId) {
        redisTemplate.opsForHash().increment("likeCount", buildHashKey(postId, "likeCount"), 1);
    }

    public void decrementLikeCount(Long postId) {
        redisTemplate.opsForHash().increment("likeCount", buildHashKey(postId, "likeCount"), -1);
    }

    private String buildHashKey(Long postId, String fieldName) {
        return postId + ":" + fieldName;
    }
}
