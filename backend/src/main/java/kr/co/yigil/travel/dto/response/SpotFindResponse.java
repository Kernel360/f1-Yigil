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
public class SpotFindResponse {
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

    public static SpotFindResponse from(Member member, Spot spot) {
        return SpotFindResponse.builder()
                .title(spot.getTitle())
                .imageUrl(spot.getImageUrl())
                .videoUrl(spot.getVideoUrl())
                .description(spot.getDescription())
                .memberNickname(member.getNickname())
                .memberImageUrl(member.getProfileImageUrl())
                .likeCount(0)
                .commentCount(0)
                .pointJson(GeojsonConverter.convertToJson(spot.getLocation()))
                .build();
    }
}
