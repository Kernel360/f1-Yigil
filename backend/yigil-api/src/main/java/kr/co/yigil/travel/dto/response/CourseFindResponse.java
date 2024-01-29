package kr.co.yigil.travel.dto.response;

import java.util.List;
import kr.co.yigil.comment.dto.response.CommentResponse;
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
public class CourseFindResponse {

    private String title;

    private String memberNickname;
    private String memberImageUrl;

    private List<SpotInCourseDto> spotInfos;
    private String lineStringJson;

    private List<CommentResponse> comments;

    public static CourseFindResponse from(Course course, List<Spot> spots,
            List<CommentResponse> comments) {
        return new CourseFindResponse(
                course.getTitle(),
                course.getMember().getNickname(),
                course.getMember().getProfileImageUrl(),
                spots.stream().map(SpotInCourseDto::from).toList(),
                GeojsonConverter.convertToJson(course.getPath()),
                comments
        );
    }
}