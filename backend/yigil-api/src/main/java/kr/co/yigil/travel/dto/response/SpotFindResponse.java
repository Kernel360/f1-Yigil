package kr.co.yigil.travel.dto.response;

import java.util.List;
import kr.co.yigil.comment.dto.response.CommentResponse;
<<<<<<< Updated upstream
import kr.co.yigil.member.domain.Member;
=======
import kr.co.yigil.member.Member;
>>>>>>> Stashed changes
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

    public static SpotFindResponse from(Spot spot, List<CommentResponse> comments) {
        return new SpotFindResponse(
<<<<<<< Updated upstream
            spot.getTitle(),
            spot.getDescription(),
            member.getNickname(),
            member.getProfileImageUrl(),
            GeojsonConverter.convertToJson(spot.getLocation()),
            comments
=======
                spot.getTitle(),
                spot.getDescription(),
                spot.getMember().getNickname(),
                spot.getMember().getProfileImageUrl(),
                GeojsonConverter.convertToJson(spot.getLocation()),
                comments
>>>>>>> Stashed changes
        );
    }
}
