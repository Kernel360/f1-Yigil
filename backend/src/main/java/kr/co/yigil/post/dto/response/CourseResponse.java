package kr.co.yigil.post.dto.response;

import java.util.List;
import kr.co.yigil.member.domain.Member;
import kr.co.yigil.post.domain.Post;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.util.GeojsonConverter;
import lombok.Builder;
import lombok.Data;

@Data
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
    private String lineStringGeoJson;

    public static CourseResponse from(Post post) {
        if(post.getTravel() instanceof Spot spot) {
            throw  new RuntimeException("여기서 스팟 부르면 안됩니다");
        }

        Course course = (Course) post.getTravel();
        List<Spot> spots = course.getSpots();

        return CourseResponse.builder()
                .title(course.getTitle())
                .memberNickname(post.getMember().getNickname())
                .memberImageUrl(post.getMember().getProfileImageUrl())
                .likeCount(0)
                .commentCount(0)
                .spotInfos(spots.stream().map(PostResponse::from2).toList())
                .lineStringGeoJson(GeojsonConverter.convertToJson(course.getPath()))
                .build();
    }

    public static CourseResponse from(Post post,Course course, List<Spot> spots) {
        return CourseResponse.builder()
                .title(course.getTitle())
                .memberNickname(post.getMember().getNickname())
                .memberImageUrl(post.getMember().getProfileImageUrl())
                .likeCount(0)
                .commentCount(0)
                .spotInfos(spots.stream().map(PostResponse::from2).toList())
                .lineStringGeoJson(GeojsonConverter.convertToJson(course.getPath()))
                .build();
    }
}
