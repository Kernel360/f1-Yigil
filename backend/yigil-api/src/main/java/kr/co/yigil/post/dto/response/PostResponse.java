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
    private Long postId;
    private Long travelId;

    private String title;
    private String imageUrl;
    private String description;

    private String memberNickname;
    private String memberImageUrl;

//    private Integer likeCount;
//    private Integer commentCount;

    public static PostResponse from(Post post) {
        if (post.getTravel() instanceof Spot spot) {
            return fromSpot(post.getId(), spot, post.getMember());
        } else if(post.getTravel() instanceof Course course){
            return fromCourse(post.getId(), course, post.getMember());
        } else{
            throw new BadRequestException(ExceptionCode.TRAVEL_CASTING_ERROR);
        }
    }

    public static PostResponse fromSpot(Long postId, Spot spot, Member member) {
        return new PostResponse(
            postId,
            spot.getId(),
            spot.getTitle(),
            spot.getFileUrl(),
            spot.getDescription(),
            member.getNickname(),
            member.getProfileImageUrl()
        );
    }

    public static PostResponse fromCourse(Long postId, Course course, Member member) {
        System.out.println("-------------------");
        System.out.println();
        Spot representativeSpot = course.getSpots().get(course.getRepresentativeSpotOrder());
        String imgUrl = representativeSpot.getFileUrl();
        String description = representativeSpot.getDescription();

        return new PostResponse(
            postId,
            course.getId(),
            course.getTitle(),
            imgUrl,
            description,
            member.getNickname(),
            member.getProfileImageUrl()
        );
    }

    // 생성자 추가
    public PostResponse(Long postId, Long travelId, String title, String imageUrl, String description, String memberNickname, String memberImageUrl) {
        this.postId = postId;
        this.travelId = travelId;
        this.title = title;
        this.imageUrl = imageUrl;
        this.description = description;
        this.memberNickname = memberNickname;
        this.memberImageUrl = memberImageUrl;
    }

}
