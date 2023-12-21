package kr.co.yigil.post.dto.request;

import java.util.List;

import kr.co.yigil.member.domain.Member;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.util.GeojsonConverter;
import lombok.Data;

@Data
public class CourseRequest {
    private String title;

    private String description;
    private String imageUrl;
    // private String videoUrl;
    private int representativeSpotOrder;

    private List<Long> spotIds;
    private String lineStringJson;

    public static Course toEntity(CourseRequest courseRequest, List<Spot> spots) {

        return Course.builder()
                .path(GeojsonConverter.convertToLineString(courseRequest.getLineStringJson()))
                .spots(spots)
                .representativeSpotOrder(courseRequest.getRepresentativeSpotOrder())
                .title(courseRequest.getTitle())
                .build();
    }
}

