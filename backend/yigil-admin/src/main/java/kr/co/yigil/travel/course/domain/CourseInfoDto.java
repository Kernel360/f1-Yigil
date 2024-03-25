package kr.co.yigil.travel.course.domain;

import kr.co.yigil.member.SocialLoginType;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.Spot;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public class CourseInfoDto {

    @Data
    public static class CoursesPageInfo {

        private final Page<CourseListUnit> courses;

        public CoursesPageInfo(List<CourseListUnit> courses, Pageable pageable,
            long totalElements) {
            this.courses = new PageImpl<>(courses, pageable, totalElements);
        }
    }

    @Data
    @AllArgsConstructor
    public static class CourseListUnit {

        private final Long courseId;
        private final String title;
        private final String description;
        private final LocalDateTime createdAt;
        private final String ownerNickname;
        private final String ownerProfileImageUrl;
        private final int spotCount;
        private final int favorCount;
        private final int commentCount;

        public CourseListUnit(Course course, CourseAdditionalInfo courseAdditionalInfo) {
            this.courseId = course.getId();
            this.title = course.getTitle();
            this.description = course.getDescription();
            this.createdAt = course.getCreatedAt();
            this.ownerNickname = course.getMember().getNickname();
            this.ownerProfileImageUrl = course.getMember().getProfileImageUrl();
            this.spotCount = course.getSpots().size();
            this.favorCount = courseAdditionalInfo.getFavorCount();
            this.commentCount = courseAdditionalInfo.getCommentCount();

        }
    }

    @Data
    public static class CourseDetailInfo {

        private final Long courseId;
        private final String title;
        private final String content;
        private final String mapStaticImageUrl;
        private final LocalDateTime createdAt;
        private final double rate;
        private final Long writerId;
        private final String writerName;
        private final String writerProfileImageUrl;
        private final SocialLoginType writerSocialLoginType;
        private final int favorCount;
        private final int commentCount;

        private final List<SpotDetailInfo> spots;

        public CourseDetailInfo(final Course course,
            final CourseAdditionalInfo courseAdditionalInfo, List<SpotDetailInfo> spots){
            this.courseId = course.getId();
            this.title = course.getTitle();
            this.content = course.getDescription();
            this.mapStaticImageUrl = course.getMapStaticImageFileUrl();
            this.createdAt = course.getCreatedAt();
            this.rate = course.getRate();
            this.writerId = course.getMember().getId();
            this.writerName = course.getMember().getNickname();
            this.writerProfileImageUrl = course.getMember().getProfileImageUrl();
            this.writerSocialLoginType = course.getMember().getSocialLoginType();
            this.favorCount = courseAdditionalInfo.getFavorCount();
            this.commentCount = courseAdditionalInfo.getCommentCount();
            this.spots = spots;
        }
    }

    @Data
    @AllArgsConstructor
    public static class CourseAdditionalInfo {

        private int favorCount;
        private int commentCount;
    }

    @Data
    public static class SpotDetailInfo{
        private final Long spotId;
        private final String title;
        private final String content;
        private final String placeName;
        private final String address;
        private final String mapStaticImageUrl;
        private final double x;
        private final double y;
        private final LocalDateTime createdAt;
        private final double rate;
        private final int favorCount;
        private final int commentCount;
        private final List<String> imageUrls;

        public SpotDetailInfo(Spot spot, int favorCount, int commentCount){
            this.spotId = spot.getId();
            this.title = spot.getTitle();
            this.content = spot.getDescription();
            this.placeName = spot.getPlace().getName();
            this.address = spot.getPlace().getAddress();
            this.mapStaticImageUrl = spot.getPlace().getMapStaticImageFileUrl();
            this.x = spot.getLocation().getX();
            this.y = spot.getLocation().getY();
            this.createdAt = spot.getCreatedAt();
            this.rate = spot.getRate();
            this.favorCount = favorCount;
            this.commentCount = commentCount;
            this.imageUrls = spot.getImageUrls();
        }
    }

}
