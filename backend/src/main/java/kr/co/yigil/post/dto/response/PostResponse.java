package kr.co.yigil.post.dto.response;

import kr.co.yigil.member.domain.Member;
import kr.co.yigil.post.domain.Post;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.domain.Travel;
import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class PostResponse {
    // 리스트 response
    private String title;
    private String imageUrl;
    private String description;

    private String memberNickname;
    private String memberImageUrl;

    private Integer likeCount;
    private Integer commentCount;

    public static PostResponse from(Post post) {
        if (post.getTravel() instanceof Spot spot) {
            return fromSpot(spot, post.getMember());
        } else if (post.getTravel() instanceof Course course) {
            return fromCourse(course, post.getMember());
        } else {
            throw new IllegalArgumentException("Unsupported type for Travel: " + post.getTravel().getClass().getName());
        }
    }
    // for spot list
    public static PostResponse fromSpot(Spot spot, Member member) {
        return PostResponse.builder()
                .title(spot.getTitle())
                .imageUrl(spot.getImageUrl())
                .description(spot.getDescription())
                .memberNickname(member.getNickname())
                .memberImageUrl(member.getProfileImageUrl())
                .likeCount(0)
                .commentCount(0)
                .build();
    }
    // for spot info in course
    public static PostResponse from2(Spot spot) {
        return PostResponse.builder()
                .title(spot.getTitle())
                .imageUrl(spot.getImageUrl())
                .description(spot.getDescription())
                .likeCount(0)
                .commentCount(0)
                .build();
    }

    public static PostResponse fromCourse(Course course, Member member) {
        var representativeSpot = course.getSpots().get(course.getRepresentativeSpotOrder());
        var imgUrl = representativeSpot.getImageUrl();
        var description = representativeSpot.getDescription();

        return PostResponse.builder()
                .title(course.getTitle())
                .imageUrl(imgUrl)
                .description(description)
                .memberNickname(member.getNickname())
                .memberImageUrl(member.getProfileImageUrl())
                .likeCount(0)
                .commentCount(0)
                .build();
    }
}
