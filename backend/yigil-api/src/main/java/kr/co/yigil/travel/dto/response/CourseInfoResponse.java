package kr.co.yigil.travel.dto.response;

import java.util.List;
import kr.co.yigil.travel.Course;
import kr.co.yigil.travel.Spot;
import kr.co.yigil.travel.dto.util.GeojsonConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseInfoResponse {

    private Long courseId;
    private String title;

    private String memberNickname;
    private String memberImageUrl;

    private List<SpotInCourseDto> spotInfos;
    private String lineStringJson;

    private int favorCount;
    private int commentCount;

    public static CourseInfoResponse from(Course course, List<Spot> spots, int favorCount, int commentCount) {
        return new CourseInfoResponse(
                course.getId(),
                course.getTitle(),
                course.getMember().getNickname(),
                course.getMember().getProfileImageUrl(),
                spots.stream().map(SpotInCourseDto::from).toList(),
                GeojsonConverter.convertToJson(course.getPath()),
                favorCount,
                commentCount
        );
    }
}