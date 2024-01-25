package kr.co.yigil.travel.dto.response;

import java.util.List;
import kr.co.yigil.comment.dto.response.CommentResponse;
import kr.co.yigil.member.domain.Member;
import kr.co.yigil.travel.Spot;
import kr.co.yigil.travel.dto.util.GeojsonConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpotFindResponse {
    // 세부 spot response
    private String title;
    private String description;

    private String memberNickname;
    private String memberImageUrl;

//    private Integer likeCount;
//    private Integer commentCount;

    private String pointJson;

    private List<CommentResponse> comments;

    public static SpotFindResponse from(Member member, Spot spot, List<CommentResponse> comments) {
        return new SpotFindResponse(
            spot.getTitle(),
            spot.getDescription(),
            member.getNickname(),
            member.getProfileImageUrl(),
            GeojsonConverter.convertToJson(spot.getLocation()),
            comments
        );
    }
}
