package kr.co.yigil.travel.dto.response;

import kr.co.yigil.member.domain.Member;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.dto.util.GeojsonConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpotFindResponse {
    // 세부 spot response
    private String title;
    private String fileUrl;
    private String description;

    private String memberNickname;
    private String memberImageUrl;

//    private Integer likeCount;
//    private Integer commentCount;

    private String pointJson;

    public static SpotFindResponse from(Member member, Spot spot) {
        return new SpotFindResponse(
            spot.getTitle(),
            spot.getFileUrl(),
            spot.getDescription(),
            member.getNickname(),
            member.getProfileImageUrl(),
            GeojsonConverter.convertToJson(spot.getLocation())
        );
    }
}
