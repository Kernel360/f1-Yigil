package kr.co.yigil.travel.dto.request;

import java.util.List;
import kr.co.yigil.member.Member;
import kr.co.yigil.travel.Course;
import kr.co.yigil.travel.Spot;
import kr.co.yigil.travel.dto.util.GeojsonConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseUpdateRequest {
    private String title;
    private String lineStringJson;

    private int representativeSpotOrder;
    private List<Long> spotIds;

    private List<Long> removedSpotIds;
    private List<Long> addedSpotIds;

    public static Course toEntity(Member member, Long courseId, CourseUpdateRequest courseUpdateRequest, List<Spot> spots) {
        return new Course(
                member,
                courseId,
                GeojsonConverter.convertToLineString(courseUpdateRequest.getLineStringJson()),
                spots,
                courseUpdateRequest.getRepresentativeSpotOrder(),
                courseUpdateRequest.getTitle()
        );
    }
}