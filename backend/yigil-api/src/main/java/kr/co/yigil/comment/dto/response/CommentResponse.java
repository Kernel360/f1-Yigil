package kr.co.yigil.comment.dto.response;

import java.util.ArrayList;
import java.util.List;
import kr.co.yigil.comment.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private Long id;
    private String content;

    private Long memberId;
    private String memberNickname;
    private String memberImageUrl;

    private String createdAt;

    private List<CommentResponse> children = new ArrayList<>();

    public CommentResponse(Long id, String content, Long memberId, String memberNickname, String memberImageUrl, String createdAt) {
        this.id = id;
        this.content = content;
        this.memberId = memberId;
        this.memberNickname = memberNickname;
        this.memberImageUrl = memberImageUrl;
        this.createdAt = createdAt;
    }

    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getMember().getId(),
                comment.getMember().getNickname(),
                comment.getMember().getProfileImageUrl(),
                comment.getCreatedAt().toString()
                );
    }

}
