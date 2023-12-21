package kr.co.yigil.post.dto.response;

import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.util.GeojsonConverter;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SpotResponse {
    // 세부 spot response
    private String title;
    private String imageUrl;
    private String videoUrl;
    private String description;

    private String memberNickname;
    private String memberImageUrl;

    private Integer likeCount;
    private Integer commentCount;

    private String pointGeoJson;

    public static SpotResponse from(Spot spot) {
        return SpotResponse.builder()
                .title(spot.getTitle())
                .imageUrl(spot.getImageUrl())
                .videoUrl(spot.getVideoUrl())
                .description(spot.getDescription())
                .memberNickname(spot.getMember().getNickname())
                .memberImageUrl(spot.getMember().getProfileImageUrl())
                .likeCount(0)
                .commentCount(0)
                .pointGeoJson(GeojsonConverter.convertToJson(spot.getLocation()))
                .build();
    }
}
