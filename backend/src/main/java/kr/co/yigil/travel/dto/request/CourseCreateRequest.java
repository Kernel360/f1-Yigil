package kr.co.yigil.travel.dto.request;

import java.util.List;

import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.dto.util.GeojsonConverter;
import lombok.Data;

@Data
public class CourseCreateRequest {
    private String title;

    private String description;
    private String imageUrl;
    // private String videoUrl;
    private int representativeSpotOrder;

    private List<Long> spotIds;
    private String lineStringJson;

    public static Course toEntity(CourseCreateRequest courseCreateRequest, List<Spot> spots) {

        return Course.builder()
                .path(GeojsonConverter.convertToLineString(courseCreateRequest.getLineStringJson()))
                .spots(spots)
                .representativeSpotOrder(courseCreateRequest.getRepresentativeSpotOrder())
                .title(courseCreateRequest.getTitle())
                .build();
    }
}

