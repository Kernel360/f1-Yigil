package kr.co.yigil.member.domain;

import java.time.LocalDateTime;
import java.util.List;
import kr.co.yigil.follow.domain.FollowCount;
import kr.co.yigil.member.Member;
import kr.co.yigil.place.Place;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.Spot;
import lombok.Getter;
import lombok.ToString;

public class MemberInfo {
    private static final String DEFAULT_PROFILE_CND = "http://cnd.yigil.co.kr";

    /**
     * 멤버 정보 조회 응답
     */
    @Getter
    @ToString
    public static class Main {

        private final Long memberId;
        private final String email;
        private final String nickname;
        private final String profileImageUrl;
        private final int followingCount;
        private final int followerCount;

        public Main(Member member, FollowCount followCount) {
            this.memberId = member.getId();
            this.email = member.getEmail();
            this.nickname = member.getNickname();
            this.profileImageUrl = DEFAULT_PROFILE_CND + "/" + member.getProfileImageUrl();
            this.followingCount = followCount.getFollowingCount();
            this.followerCount = followCount.getFollowerCount();
        }
    }

    /**
     * 멤버 정보의 코스 조회 응답
     * courseList : 코스 리스트
     * pageInfo : 페이지 정보
     */

    @Getter
    @ToString
    public static class CourseListResponse {

        private final List<CourseInfo> content;
        private final int totalPages;
        public CourseListResponse(List<CourseInfo> courseList, int totalPages) {
            this.content = courseList;
            this.totalPages = totalPages;
        }
    }

    @Getter
    @ToString
    public static class CourseInfo {

        private final Long courseId;
        private final String title;
        private final Double rate;
        private final Integer spotCount;
        private final LocalDateTime createdDate;
        private final String mapStaticImageUrl;
        private final Boolean isPrivate;

        public CourseInfo(Course course) {
            this.courseId = course.getId();
            this.title = course.getTitle();
            this.rate = course.getRate();
            this.spotCount = course.getSpots().size();
            this.createdDate = course.getCreatedAt();
            this.isPrivate = course.isPrivate();
            this.mapStaticImageUrl = course.getMapStaticImageFile().getFileUrl();
        }
    }

    @Getter
    @ToString
    public static class SpotListResponse {

        private final List<SpotInfo> content;
        private final int totalPages;

        public SpotListResponse(List<SpotInfo> spotList, int totalPages) {
            this.content = spotList;
            this.totalPages = totalPages;
        }
    }

    @Getter
    @ToString
    public static class SpotInfo {

        private final Long spotId;
        private final String title;
        private final double rate;
        private final String imageUrl;
        private final LocalDateTime createdDate;
        private final Boolean isPrivate;

        public SpotInfo(Spot spot) {
            this.spotId = spot.getId();
            this.title = spot.getTitle();
            this.rate = spot.getRate();
            this.imageUrl = spot.getAttachFiles().getUrls().getFirst();
            this.createdDate = spot.getCreatedAt();
            this.isPrivate = spot.isPrivate();
        }
    }

    @Getter
    @ToString
    public static class PlaceInfo {

        private final String placeName;
        private final String placeAddress;
        private final String mapStaticImageUrl;
        private final String placeImageUrl;

        public PlaceInfo(Place place) {
            this.placeName = place.getName();
            this.placeAddress = place.getAddress();
            this.mapStaticImageUrl = place.getMapStaticImageFile().getFileUrl();
            this.placeImageUrl = place.getImageFile().getFileUrl();
        }
    }

    @Getter
    @ToString
    public static class FollowerResponse {

        private final List<FollowInfo> content;
        private final boolean hasNext;

        public FollowerResponse(List<FollowInfo> content, boolean hasNext) {
            this.content = content;
            this.hasNext = hasNext;
        }
    }

    @Getter
    @ToString
    public static class FollowingResponse {

        private final List<FollowInfo> content;
        private final boolean hasNext;

        public FollowingResponse(List<FollowInfo> content, boolean hasNext) {
            this.content = content;
            this.hasNext = hasNext;
        }
    }

    @Getter
    @ToString
    public static class FollowInfo {

            private final Long memberId;
            private final String nickname;
            private final String profileImageUrl;

            public FollowInfo(Member member) {
                this.memberId = member.getId();
                this.nickname = member.getNickname();
                this.profileImageUrl = member.getProfileImageUrl();
            }
    }

    @Getter
    @ToString
    public static class MemberUpdateResponse {
        private final String message;

        public MemberUpdateResponse(String message) {
            this.message = message;
        }
    }

    @Getter
    @ToString
    public static class VisibilityChangeResponse {
        private final String message;

        public VisibilityChangeResponse(String message) {
            this.message = message;
        }
    }

    @Getter
    @ToString
    public static class MemberDeleteResponse {
        private final String message;
        public MemberDeleteResponse(String message) {
            this.message = message;
        }
    }
}
