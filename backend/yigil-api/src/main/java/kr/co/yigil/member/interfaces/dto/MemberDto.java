package kr.co.yigil.member.interfaces.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TravelsVisibilityRequest {

        private List<Long> travelIds;
        private Boolean isPrivate;
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
    public static class MemberCourseResponse {

        private final List<CourseInfo> courseList;
        private final int totalPages;
    }

    @Getter
    @Builder
    @ToString
    public static class MemberSpotResponse {

        private List<SpotInfo> spotList;
        private int totalPages;
    }

    @Getter
    @Builder
    @ToString
    public static class TravelsVisibilityResponse {

        private final String message;
    }

    @Getter
    @Builder
    @ToString
    public static class SpotsVisibilityResponse {

        private final String message;
    }


    @Getter
    @Builder
    @ToString
    public static class CourseInfo {

        private final Long courseId;
        private final String title;
        private final String description;
        private final Double rate;
        private final Integer spotCount;
        private final String createdDate;
        private final String mapStaticImageUrl;
        private final Boolean isPrivate;
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
        private final String imageUrl;
        private final String createdDate;
        private final PlaceInfo placeInfo;
        private final Boolean isPrivate;
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

    @Getter
    @Builder
    @ToString
    public static class FollowerResponse {

        private final List<FollowInfo> followerList;
        private final boolean hasNext;
    }

    @Getter
    @Builder
    @ToString
    public static class FollowingResponse {

        private final List<FollowInfo> followingList;
        private final boolean hasNext;
    }

    @Getter
    @Builder
    @ToString
    public static class FollowInfo {

        private final Long memberId;
        private final String nickname;
        private final String profileImageUrl;
    }

    @Getter
    @Builder
    @ToString
    public static class MemberDeleteResponse {
        private final String message;
    }
}
