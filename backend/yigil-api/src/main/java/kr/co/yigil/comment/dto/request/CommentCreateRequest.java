package kr.co.yigil.comment.dto.request;

import kr.co.yigil.comment.domain.Comment;
import kr.co.yigil.member.Member;
import kr.co.yigil.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentCreateRequest {
    private String content;
    private Long parentId;
    private Long notifiedMemberId;

    public static Comment toEntity(CommentCreateRequest commentCreateRequest, Member member, Post post) {
        return new Comment(
                commentCreateRequest.getContent(),
                member,
                post
        );
    }
}
