package kr.co.yigil.travel.interfaces.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class MyFavoriteCoursesResponse {
    List<FavoriteCourseDto> contents;
    boolean hasNext;

    @Data
    public static class FavoriteCourseDto {
        private Long courseId;
        private String title;
        private Double rate;
        private Integer spotCount;
        private String createdDate;
        private String mapStaticImageUrl;

        private Long writerId;
        private String writerNickname;
        private String writerProfileImageUrl;
        private boolean following;

    }

}
