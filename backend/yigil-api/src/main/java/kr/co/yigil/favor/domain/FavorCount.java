package kr.co.yigil.favor.domain;

import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@Getter
@AllArgsConstructor
@RedisHash("favorCount")
public class FavorCount {

    @Id
    private Long postId;

    private int favorCount;

    public void incrementFavorCount() { favorCount++; }

    public void decrementFavorCount() { favorCount--; }
}
