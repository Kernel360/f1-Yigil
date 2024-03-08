package kr.co.yigil.travel.course.domain;

import java.time.LocalDateTime;
import java.util.List;
import kr.co.yigil.travel.domain.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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
        private final LocalDateTime createdAt;
        private final int favorCount;
        private final int commentCount;

        public CourseListUnit(Course course, CourseAdditionalInfo courseAdditionalInfo) {
            this.courseId = course.getId();
            this.title = course.getTitle();
            this.createdAt = course.getCreatedAt();
            this.favorCount = courseAdditionalInfo.getFavorCount();
            this.commentCount = courseAdditionalInfo.getCommentCount();

        }
    }

    @Data
    @AllArgsConstructor
    public static class CourseDetailInfo {

        private final Long courseId;
        private final String title;
        private final String content;
        private final String mapStaticImageUrl;
        private final LocalDateTime createdAt;
        private final double rate;
        private final Long writerId;
        private final String writerName;
        private final int favorCount;
        private final int commentCount;

        public CourseDetailInfo(final Course course,
            final CourseAdditionalInfo courseAdditionalInfo) {
            this.courseId = course.getId();
            this.title = course.getTitle();
            this.content = course.getDescription();
            this.mapStaticImageUrl = course.getMapStaticImageFileUrl();
            this.createdAt = course.getCreatedAt();
            this.rate = course.getRate();
            this.writerId = course.getMember().getId();
            this.writerName = course.getMember().getNickname();
            this.favorCount = courseAdditionalInfo.getFavorCount();
            this.commentCount = courseAdditionalInfo.getCommentCount();
        }
    }

    @Data
    @AllArgsConstructor
    public static class CourseAdditionalInfo {

        private int favorCount;
        private int commentCount;
    }

}
