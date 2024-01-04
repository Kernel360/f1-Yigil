package kr.co.yigil.travel.dto.response;

import java.util.List;
import kr.co.yigil.member.domain.Member;
import kr.co.yigil.post.domain.Post;
import kr.co.yigil.post.dto.response.PostResponse;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.dto.SpotInCourseDto;
import kr.co.yigil.travel.dto.util.GeojsonConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseFindResponse {
    private String title;

    private String memberNickname;
    private String memberImageUrl;

    private Integer likeCount;
    private Integer commentCount;

    // 코스에 포함된 스팟 정보
    private List<SpotInCourseDto> spotInfos;
    private String lineStringJson;

    public static CourseFindResponse from(Post post, Course course, List<Spot> spots) {
        return new CourseFindResponse(
            course.getTitle(),
            post.getMember().getNickname(),
            post.getMember().getProfileImageUrl(),
            0, // likeCount 초기화
            0, // commentCount 초기화
            spots.stream().map(SpotInCourseDto::from).toList(),
            GeojsonConverter.convertToJson(course.getPath())
        );
    }
}