package kr.co.yigil.travel.dto.response;

import java.util.List;
import kr.co.yigil.member.Member;
import kr.co.yigil.travel.Course;
import kr.co.yigil.travel.Spot;
import kr.co.yigil.travel.dto.SpotInCourseDto;
import kr.co.yigil.travel.dto.util.GeojsonConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseUpdateResponse {
    private String title;

    private String memberNickname;
    private String memberImageUrl;

    private Integer likeCount;
    private Integer commentCount;

    private List<SpotInCourseDto> spotInfos;
    private String lineStringJson;

    public static CourseUpdateResponse from(Member member, Course course, List<Spot> spots) {
        return new CourseUpdateResponse(
            course.getTitle(),
            member.getNickname(),
            member.getProfileImageUrl(),
            0,
            0,
            spots.stream().map(SpotInCourseDto::from).toList(),
            GeojsonConverter.convertToJson(course.getPath())
        );
    }
}
