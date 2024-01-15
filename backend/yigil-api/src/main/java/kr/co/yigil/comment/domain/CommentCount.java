package kr.co.yigil.comment.domain;

import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@Getter
@AllArgsConstructor
@RedisHash("commentCount")
public class CommentCount {

    @Id
    private Long travelId;

    private final int commentCount;
}
