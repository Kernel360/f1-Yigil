package kr.co.yigil.travel.dto.response;

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
public class SpotUpdateResponse {
<<<<<<< Updated upstream
    private String title;
    private String description;
    private String memberNickname;
    private String memberImageUrl;
    private String pointJson;

    public static SpotUpdateResponse from(Member member, Spot spot) {
        return new SpotUpdateResponse(
            spot.getTitle(),
            spot.getDescription(),
            member.getNickname(),
            member.getProfileImageUrl(),
            GeojsonConverter.convertToJson(spot.getLocation())
        );
    }
=======
    private String message;
>>>>>>> Stashed changes

}
