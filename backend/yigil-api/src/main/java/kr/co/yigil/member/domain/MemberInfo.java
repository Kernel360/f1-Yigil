package kr.co.yigil.member.domain;

import java.util.List;
import kr.co.yigil.follow.domain.FollowCount;
import kr.co.yigil.member.Member;
import kr.co.yigil.place.domain.Place;
import kr.co.yigil.region.domain.Region;
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
        private final String ages;
        private final String gender;
        private final List<FavoriteRegionInfo> favoriteRegions;
        private final int followingCount;
        private final int followerCount;

        public Main(Member member, FollowCount followCount) {
            this.memberId = member.getId();
            this.email = member.getEmail();
            this.nickname = member.getNickname();
            this.profileImageUrl = member.getProfileImageUrl();
            this.ages = member.getAges().getViewName();
            this.gender = member.getGender().getViewName();
            this.followingCount = followCount.getFollowingCount();
            this.followerCount = followCount.getFollowerCount();
            this.favoriteRegions = member.getFavoriteRegions().stream().
                map(MemberInfo.FavoriteRegionInfo::new)
                .toList();
        }
    }

    @Getter
    public static class FavoriteRegionInfo {

        private final Long id;
        private final String name;

        public FavoriteRegionInfo(Region region) {
            this.id = region.getId();
            this.name = region.getName();
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

    @Getter
    public static class NicknameCheckInfo {
        private final boolean available;

        public NicknameCheckInfo(boolean isAvailable) {
            this.available = isAvailable;
        }
    }
}
