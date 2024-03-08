package kr.co.yigil.travel.course.interfaces.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

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
        private LocalDateTime createdAt;
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
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CourseDeleteResponse {
        private String message;
    }
}
