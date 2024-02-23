package kr.co.yigil.member.domain;

import java.time.LocalDateTime;
import java.util.List;
import kr.co.yigil.member.Member;
import kr.co.yigil.place.Place;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.util.GeojsonConverter;
import lombok.Getter;
import lombok.ToString;

public class MemberInfo {

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

        public Main(Member member, int followingCount, int followerCount) {
            this.memberId = member.getId();
            this.email = member.getEmail();
            this.nickname = member.getNickname();
            this.profileImageUrl = member.getProfileImageUrl();
            this.followingCount = followingCount;
            this.followerCount = followerCount;
        }
    }

    /**
     * 멤버 정보의 코스 조회 응답
     * courseList : 코스 리스트
     * pageInfo : 페이지 정보
     */

    @Getter
    @ToString
    public static class MemberCourseResponse {

        private final List<CourseInfo> courseList;
        private final PageInfo pageInfo;
        public MemberCourseResponse(List<CourseInfo> courseList, int totalPages) {
            this.courseList = courseList;
            this.pageInfo = new PageInfo(totalPages);
        }
    }

    @Getter
    @ToString
    public static class CourseInfo {

        private final Long courseId;
        private final String title;
        private final String description;
        private final String lineStringJson;
        private final double rate;
        private final int spotCount;
        private final LocalDateTime createdDate;


        public CourseInfo(Course course) {
            this.courseId = course.getId();
            this.title = course.getTitle();
            this.description = course.getDescription();
            this.lineStringJson = GeojsonConverter.convertToJson(course.getPath());
            this.rate = course.getRate();
            this.spotCount = course.getSpots().size();
            this.createdDate = course.getCreatedAt();
        }
    }

    @Getter
    @ToString
    public static class MemberSpotResponse {

        private final List<SpotInfo> spotList;
        private final PageInfo pageInfo;

        public MemberSpotResponse(List<SpotInfo> spotList, int pageInfo) {
            this.spotList = spotList;
            this.pageInfo = new PageInfo(pageInfo);
        }
    }

    @Getter
    @ToString
    public static class PageInfo{
        private final int totalPages;

        public PageInfo(int totalPages) {
            this.totalPages = totalPages;
        }
    }

    @Getter
    @ToString
    public static class SpotInfo {

        private final Long spotId;
        private final String title;
        private final String description;
        private final String pointJson;
        private final double rate;
        private final List<String> imageUrlList;
        private final LocalDateTime createdDate;
        private final PlaceInfo placeInfo;
        public SpotInfo(Spot spot) {
            this.spotId = spot.getId();
            this.title = spot.getTitle();
            this.description = spot.getDescription();
            this.pointJson = GeojsonConverter.convertToJson(spot.getLocation());
            this.rate = spot.getRate();
            this.imageUrlList = spot.getAttachFiles().getUrls();
            this.createdDate = spot.getCreatedAt();
            this.placeInfo = new PlaceInfo(spot.getPlace());        }
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
    public static class MemberDeleteResponse {

        private final String message;

        public MemberDeleteResponse(String message) {
            this.message = message;
        }
    }

    @Getter
    @ToString
    public static class MemberFollowResponse {

        private final List<FollowerInfo> followerList;

        public MemberFollowResponse(List<FollowerInfo> followerList) {
            this.followerList = followerList;
        }
    }

    @Getter
    @ToString
    public static class FollowerInfo{

            private final Long memberId;
            private final String nickname;
            private final String profileImageUrl;

            public FollowerInfo(Member member) {
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

}
