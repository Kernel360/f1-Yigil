package kr.co.yigil.travel.interfaces.dto.response;


import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class MyCoursesResponse {
    private final List<CourseInfo> content;
    private final int totalPages;

    @Getter
    @Builder
    @ToString
    public static class CourseInfo {

        private final Long courseId;
        private final String title;
        private final Double rate;
        private final Integer spotCount;
        private final String createdDate;
        private final String mapStaticImageUrl;
        private final Boolean isPrivate;
    }
}
