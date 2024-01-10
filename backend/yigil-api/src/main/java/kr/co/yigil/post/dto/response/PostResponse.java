package kr.co.yigil.post.dto.response;

import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.member.domain.Member;
import kr.co.yigil.post.domain.Post;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.Spot;
import lombok.Data;
import lombok.Setter;

@Data
public class PostResponse {
    // 리스트 response
    private String title;
    private String imageUrl;
    private String description;

    private String memberNickname;
    private String memberImageUrl;

//    private Integer likeCount;
//    private Integer commentCount;

    public static PostResponse from(Post post) {
        if (post.getTravel() instanceof Spot spot) {
            return fromSpot(spot, post.getMember());
        } else if(post.getTravel() instanceof Course course){
            return fromCourse(course, post.getMember());
        } else{
            throw new BadRequestException(ExceptionCode.TRAVEL_CASTING_ERROR);
        }
    }

    public static PostResponse fromSpot(Spot spot, Member member) {
        return new PostResponse(
            spot.getTitle(),
            spot.getFileUrl(),
            spot.getDescription(),
            member.getNickname(),
            member.getProfileImageUrl()
        );
    }

    public static PostResponse fromCourse(Course course, Member member) {
        var representativeSpot = course.getSpots().                                                                                                                                                                     get(course.getRepresentativeSpotOrder());
        var imgUrl = representativeSpot.getFileUrl();
        var description = representativeSpot.getDescription();

        return new PostResponse(
            course.getTitle(),
            imgUrl,
            description,
            member.getNickname(),
            member.getProfileImageUrl()
        );
    }

    // 생성자 추가
    public PostResponse(String title, String imageUrl, String description, String memberNickname, String memberImageUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.description = description;
        this.memberNickname = memberNickname;
        this.memberImageUrl = memberImageUrl;
    }

}
