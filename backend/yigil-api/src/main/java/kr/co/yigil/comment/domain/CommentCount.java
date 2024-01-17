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
    private Long postId;

    private int commentCount;

    public void incrementCommentCount() { commentCount++; }

    public void decrementCommentCount(int deletedCommentCount) { commentCount-=deletedCommentCount;}
}
