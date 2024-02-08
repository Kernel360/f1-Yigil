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

    /**
     * 아래와 같이 Constants로 관리해보는것도 좋을것 같습니다.
     * private static final String DELETED_COMMENT = "삭제된 댓글입니다.";
     * */
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

    public void addChild(CommentResponse commentResponse){
        this.children.add(commentResponse);
    }

    public static CommentResponse from(Comment comment) {
        String content;

        // 메세지는 Constants로 관리하면 어떨까요?
        if(comment.isDeleted()){
            content = "삭제된 댓글입니다.";
        }else{
            content = comment.getContent();
        }
        return new CommentResponse(
            comment.getId(),
            content,
            comment.getMember().getId(),
            comment.getMember().getNickname(),
            comment.getMember().getProfileImageUrl(),
            comment.getCreatedAt().toString()
        );
    }

}
