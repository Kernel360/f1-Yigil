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

        private final List<FollowBasicInfo> content;
        private final boolean hasNext;

        public FollowersResponse(List<FollowBasicInfo> content, boolean hasNext) {
            this.content = content;
            this.hasNext = hasNext;
        }
    }

    @Getter
    @ToString
    public static class FollowingsResponse {

        private final List<FollowBasicInfo> content;
        private final boolean hasNext;

        public FollowingsResponse(List<FollowBasicInfo> content, boolean hasNext) {
            this.content = content;
            this.hasNext = hasNext;
        }
    }

    @Getter
    @ToString
    public static class FollowBasicInfo {

        private final Long memberId;
        private final String nickname;
        private final String profileImageUrl;

        public FollowBasicInfo(Member member) {
            this.memberId = member.getId();
            this.nickname = member.getNickname();
            this.profileImageUrl = member.getProfileImageUrl();
        }
    }
}
