package kr.co.yigil.travel.dto.request;

import java.util.List;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.dto.util.GeojsonConverter;
import lombok.Data;

@Data
public class CourseUpdateRequest {
    private String title;

    private String description;
    private String imageUrl;
    // private String videoUrl;
    private int representativeSpotOrder;

    private List<Long> spotIds;
    private String lineStringJson;
    private List<Long> removedSpotIds;
    private List<Long> addedSpotIds;

    public static Course toEntity(CourseUpdateRequest courseUpdateRequest, List<Spot> spots) {

        return Course.builder()
                .path(GeojsonConverter.convertToLineString(courseUpdateRequest.getLineStringJson()))
                .spots(spots)
                .representativeSpotOrder(courseUpdateRequest.getRepresentativeSpotOrder())
                .title(courseUpdateRequest.getTitle())
                .build();
    }
}
