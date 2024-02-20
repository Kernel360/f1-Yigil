package kr.co.yigil.travel.interfaces.dto.response;

import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.interfaces.dto.util.GeojsonConverter;
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

    private Integer favorCount;
    private Integer commentCount;

    private String pointJson;

    public static SpotInfoResponse from(Spot spot, int favorCount, int commentCount) {
        return new SpotInfoResponse(
                spot.getId(),
                spot.getTitle(),
                spot.getDescription(),
                spot.getMember().getNickname(),
                spot.getMember().getProfileImageUrl(),
                favorCount,
                commentCount,
                GeojsonConverter.convertToJson(spot.getLocation())
        );
    }
}
