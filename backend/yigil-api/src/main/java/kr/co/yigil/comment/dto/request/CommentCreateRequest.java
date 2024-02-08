package kr.co.yigil.comment.dto.request;

import kr.co.yigil.comment.domain.Comment;
import kr.co.yigil.member.Member;
import kr.co.yigil.travel.Travel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Request와 Response 패키지를 application 레벨로 옮기는건 어떻게 생각하시나요 ?
// Lombok을 이용하였는데 @Data 보다는 필수로 사용하는것만 추가하는것을 추천드립니다.
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentCreateRequest {
    private String content;
    private Long parentId;
    private Long notifiedMemberId;

    // 사용하지 않는 코드는 삭제해주세요!
    public static Comment toEntity(CommentCreateRequest commentCreateRequest, Member member, Travel travel) {
        return new Comment(
                commentCreateRequest.getContent(),
                member,
                travel
        );
    }
}
