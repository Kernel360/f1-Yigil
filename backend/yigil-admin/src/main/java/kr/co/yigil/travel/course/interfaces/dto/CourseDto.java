package kr.co.yigil.travel.course.interfaces.dto;

import kr.co.yigil.member.SocialLoginType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public class CourseDto {


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CoursesResponse {
        private Page<CourseListUnit> courses;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CourseListUnit {
        private Long courseId;
        private String title;
        private String description;
        private LocalDateTime createdAt;
        private String ownerNickname;
        private String ownerProfileImageUrl;
        private int spotCount;
        private int favorCount;
        private int commentCount;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CourseDetailResponse {
        private Long courseId;
        private String title;
        private String content;

        private String mapStaticImageUrl;
        private LocalDateTime createdAt;
        private double rate;
        private int favorCount;
        private int commentCount;

        private Long writerId;
        private String writerName;
        private String writerProfileImageUrl;
        private SocialLoginType writerSocialLoginType;

        private List<SpotDetailDto> spots;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CourseDeleteResponse {
        private String message;
    }

    @Data
    public static class SpotDetailDto {
        private Long spotId;
        private String title;
        private String content;

        private String placeName;
        private String address;
        private String mapStaticImageUrl;
        private double x;
        private double y;

        private LocalDateTime createdAt;
        private double rate;
        private int favorCount;
        private int commentCount;
        private List<String> imageUrls;
    }
}
