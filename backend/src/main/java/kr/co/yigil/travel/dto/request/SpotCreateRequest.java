package kr.co.yigil.travel.dto.request;

import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.dto.util.GeojsonConverter;
import lombok.Data;

@Data
public class SpotCreateRequest {
    private String pointJson;
    private Boolean isInCourse;
    private String title;
    private String description;
    private String imageUrl;
    private String videoUrl;

    public static Spot toEntity(SpotCreateRequest spotCreateRequest) {
        return Spot.builder()
                .location(GeojsonConverter.convertToPoint(spotCreateRequest.getPointJson()))
                .isInCourse(spotCreateRequest.getIsInCourse())
                .title(spotCreateRequest.getTitle())
                .description(spotCreateRequest.getDescription())
                .imageUrl(spotCreateRequest.getImageUrl())
                .videoUrl(spotCreateRequest.getVideoUrl())
                .build();
    }
}
