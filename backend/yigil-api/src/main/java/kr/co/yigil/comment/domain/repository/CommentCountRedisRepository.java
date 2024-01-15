package kr.co.yigil.comment.domain.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentCountRedisRepository {
    private final RedisTemplate<String, Object> redisTemplate;

    public void incrementCommentCount(Long travelId) {
        redisTemplate.opsForHash().increment("commentCount", travelId, 1);
    }

    public void decrementCommentCount(Long travelId, int deletedCommentCount) {
        redisTemplate.opsForHash().increment("commentCount", travelId, -deletedCommentCount);
    }

}
