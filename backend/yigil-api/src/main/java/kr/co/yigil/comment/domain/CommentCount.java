package kr.co.yigil.comment.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@AllArgsConstructor
@RedisHash("commentCount")
public class CommentCount {

    @Id
    private Long travelId;

    private int commentCount;

    public void incremenCommentCount() { commentCount++; }

    public void decrementCommentCount() { commentCount--;}
}
