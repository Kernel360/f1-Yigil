package kr.co.yigil.like.domain;

import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@Getter
@AllArgsConstructor
@RedisHash("\"likeCount\"")
public class LikeCount {

    @Id
    private Long postId;

    private int likeCount;

    public void incrementLikeCount() { likeCount++; }

    public void decrementLikeCount() { likeCount--; }
}
