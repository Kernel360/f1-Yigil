package kr.co.yigil.travel.dto.response;

import java.util.List;
import kr.co.yigil.post.domain.Post;
import kr.co.yigil.post.dto.response.PostResponse;
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
@Builder
public class CourseResponse {
    // 세부 코스 response
    private String title;

    private String memberNickname;
    private String memberImageUrl;

    private Integer likeCount;
    private Integer commentCount;

    // 코스에 포함된 스팟 정보
    private List<PostResponse> spotInfos;
    private String lineStringJson;

    public static CourseResponse from(Post post, Course course, List<Spot> spots) {
        return CourseResponse.builder()
                .title(course.getTitle())
                .memberNickname(post.getMember().getNickname())
                .memberImageUrl(post.getMember().getProfileImageUrl())
                .likeCount(0)
                .commentCount(0)
                .spotInfos(spots.stream().map(PostResponse::from2).toList())
                .lineStringJson(GeojsonConverter.convertToJson(course.getPath()))
                .build();
    }
}
