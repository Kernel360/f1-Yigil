package kr.co.yigil.member.interfaces.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

public class MemberDto {


    @Getter
    @Builder
    @ToString
    public static class MemberUpdateRequest {

        private String nickname;
        private String ages;
        private String gender;
        private MultipartFile profileImageFile;
    }

    @Getter
    @Builder
    @ToString
    public static class Main {

        private final Long memberId;
        private final String email;
        private final String nickname;
        private final String profileImageUrl;
        private final int followingCount;
        private final int followerCount;
    }

    @Getter
    @Builder
    @ToString
    public static class PageInfo {

        private final int totalPages;
    }

    @Getter
    @Builder
    @ToString
    public static class MemberCourseResponse {

        private final List<CourseInfo> courseList;
        private final PageInfo pageInfo;
    }

    @Getter
    @Builder
    @ToString
    public static class MemberSpotResponse {

        private List<SpotInfo> spotList;
        private PageInfo pageInfo;
    }


    @Getter
    @Builder
    @ToString
    public static class CourseInfo {

        private final Long courseId;
        private final String title;
        private final String description;
        private final String lineStringJson;
        private final double rate;
        private final int spotCount;
        private final String createdDate;
    }

    @Getter
    @Builder
    @ToString
    public static class SpotInfo {

        private final Long spotId;
        private final String title;
        private final String description;
        private final String pointJson;
        private final double rate;
        private final List<String> imageUrlList;
        private final String createdDate;
        private final PlaceInfo placeInfo;
    }

    @Getter
    @Builder
    @ToString
    public static class PlaceInfo {

        private final String placeName;
        private final String placeAddress;
        private final String mapStaticImageUrl;
        private final String placeImageUrl;
    }

    @Getter
    @Builder
    @ToString
    public static class MemberUpdateResponse {

        private final String message;
    }

}
