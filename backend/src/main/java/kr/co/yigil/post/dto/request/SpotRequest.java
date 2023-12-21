package kr.co.yigil.post.dto.request;

import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.util.GeojsonConverter;
import lombok.Data;

@Data
public class SpotRequest {

    // 뭘 해야 하나요 알려쥇;요

    private String pointJson;
    private Boolean isInCourse = false;
    private String title;
    private String description;
    private String imageUrl;
    private String videoUrl;

    public static Spot toEntity(SpotRequest spotRequest) {
        return Spot.builder()
                .location(GeojsonConverter.convertToPoint(spotRequest.getPointJson()))
                .isInCourse(spotRequest.getIsInCourse())
                .title(spotRequest.getTitle())
                .description(spotRequest.getDescription())
                .imageUrl(spotRequest.getImageUrl())
                .videoUrl(spotRequest.getVideoUrl())
                .build();
    }
}
