package kr.co.yigil.member.domain;

import java.util.List;
import kr.co.yigil.follow.domain.FollowCount;
import kr.co.yigil.member.Member;
import kr.co.yigil.place.Place;
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
    public static class MemberDeleteResponse {
        private final String message;
        public MemberDeleteResponse(String message) {
            this.message = message;
        }
    }
}
