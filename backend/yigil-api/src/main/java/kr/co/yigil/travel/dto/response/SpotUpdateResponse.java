package kr.co.yigil.travel.dto.response;

import kr.co.yigil.member.domain.Member;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.dto.util.GeojsonConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpotUpdateResponse {
    private String title;
    private String fileUrl;
    private String description;
    private String memberNickname;
    private String memberImageUrl;
    private String pointJson;

    public static SpotUpdateResponse from(Member member, Spot spot) {
        return new SpotUpdateResponse(
            spot.getTitle(),
            spot.getFileUrl(),
            spot.getDescription(),
            member.getNickname(),
            member.getProfileImageUrl(),
            GeojsonConverter.convertToJson(spot.getLocation())
        );
    }

}
