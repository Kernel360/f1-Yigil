package kr.co.yigil.travel.dto.response;

import java.util.List;
import kr.co.yigil.comment.dto.response.CommentResponse;
import kr.co.yigil.travel.Spot;
import kr.co.yigil.travel.dto.util.GeojsonConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpotInfoResponse {

    private Long spotId;
    private String title;
    private String description;

    private String memberNickname;
    private String memberImageUrl;

//    private Integer favorCount;
//    private Integer commentCount;

    private String pointJson;

    private List<CommentResponse> comments;

    public static SpotInfoResponse from(Spot spot, List<CommentResponse> comments) {
        return new SpotInfoResponse(
                spot.getId(),
                spot.getTitle(),
                spot.getDescription(),
                spot.getMember().getNickname(),
                spot.getMember().getProfileImageUrl(),
                GeojsonConverter.convertToJson(spot.getLocation()),
                comments
        );
    }
}
