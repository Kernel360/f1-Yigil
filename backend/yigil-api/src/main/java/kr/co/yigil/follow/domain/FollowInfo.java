package kr.co.yigil.follow.domain;

import java.util.List;
import kr.co.yigil.member.Member;
import lombok.Getter;
import lombok.ToString;

public class FollowInfo {

    private FollowInfo(){}
    @Getter
    @ToString
    public static class FollowersResponse {

        private final List<FollowerInfo> content;
        private final boolean hasNext;

        public FollowersResponse(List<FollowerInfo> content, boolean hasNext) {
            this.content = content;
            this.hasNext = hasNext;
        }
    }

    @Getter
    @ToString
    public static class FollowingsResponse {

        private final List<FollowingInfo> content;
        private final boolean hasNext;

        public FollowingsResponse(List<FollowingInfo> content, boolean hasNext) {
            this.content = content;
            this.hasNext = hasNext;
        }
    }

    @Getter
    @ToString
    public static class FollowingInfo {

        private final Long memberId;
        private final String nickname;
        private final String profileImageUrl;

        public FollowingInfo(Member member) {
            this.memberId = member.getId();
            this.nickname = member.getNickname();
            this.profileImageUrl = member.getProfileImageUrl();
        }
    }

    @Getter
    @ToString
    public static class FollowerInfo {

            private final Long memberId;
            private final String nickname;
            private final String profileImageUrl;
            private final Boolean isFollowing;

            public FollowerInfo(Member member, Boolean isFollowing) {
                this.memberId = member.getId();
                this.nickname = member.getNickname();
                this.profileImageUrl = member.getProfileImageUrl();
                this.isFollowing = isFollowing;
            }

    }
}
