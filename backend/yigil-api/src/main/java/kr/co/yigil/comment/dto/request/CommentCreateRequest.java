package kr.co.yigil.comment.dto.request;

import kr.co.yigil.comment.domain.Comment;
import kr.co.yigil.member.Member;
import kr.co.yigil.travel.Travel;
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

    public static Comment toEntity(CommentCreateRequest commentCreateRequest, Member member, Travel travel) {
        return new Comment(
                commentCreateRequest.getContent(),
                member,
                travel
        );
    }
}
