package kr.co.yigil.travel.dto.request;

import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.dto.util.GeojsonConverter;
import lombok.Data;

@Data
public class SpotUpdateRequest {
    private String pointJson;
    private Boolean isInCourse;
    private String title;
    private String description;
    private String imageUrl;
    private String videoUrl;

    public static Spot toEntity(SpotUpdateRequest spotUpdateRequest) {
        return Spot.builder()
                .location(GeojsonConverter.convertToPoint(spotUpdateRequest.getPointJson()))
                .isInCourse(spotUpdateRequest.getIsInCourse())
                .title(spotUpdateRequest.getTitle())
                .description(spotUpdateRequest.getDescription())
                .imageUrl(spotUpdateRequest.getImageUrl())
                .videoUrl(spotUpdateRequest.getVideoUrl())
                .build();
    }
}
