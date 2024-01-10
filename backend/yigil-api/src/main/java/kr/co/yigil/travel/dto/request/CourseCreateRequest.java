package kr.co.yigil.travel.dto.request;

import java.util.List;

import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.dto.util.GeojsonConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseCreateRequest {
    private String title;
    private int representativeSpotOrder;
    private List<Long> spotIds;
    private String lineStringJson;

    public static Course toEntity(CourseCreateRequest courseCreateRequest, List<Spot> spots) {
        return new Course(
            GeojsonConverter.convertToLineString(courseCreateRequest.getLineStringJson()),
            spots,
            courseCreateRequest.getRepresentativeSpotOrder(),
            courseCreateRequest.getTitle()
        );
    }
}
