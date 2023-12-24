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
@Builder
public class SpotUpdateResponse {
    // 세부 spot response
    private String title;
    private String imageUrl;
    private String videoUrl;
    private String description;

    private String memberNickname;
    private String memberImageUrl;

    private Integer likeCount;
    private Integer commentCount;

    private String pointJson;

    public static SpotUpdateResponse from(Member member, Spot spot) {
        return SpotUpdateResponse.builder()
                .title(spot.getTitle())
                .imageUrl(spot.getImageUrl())
                .videoUrl(spot.getVideoUrl())
                .description(spot.getDescription())
                .memberNickname(member.getNickname())
                .memberImageUrl(member.getProfileImageUrl())
                .likeCount(0) // lik, comment 는 모든 post에 있나요? 아니면 course post에만 있나요?
                .commentCount(0)
                .pointJson(GeojsonConverter.convertToJson(spot.getLocation()))
                .build();
    }
}
