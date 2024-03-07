package kr.co.yigil.follow.interfaces.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class FollowDto {

    @Getter
    @Builder
    @ToString
    public static class FollowersResponse {

        private final List<FollowerInfo> content;
        private final boolean hasNext;
    }

    @Getter
    @Builder
    @ToString
    public static class FollowingsResponse {

        private final List<FollowingInfo> content;
        private final boolean hasNext;
    }

    @Getter
    @Builder
    @ToString
    public static class FollowingInfo {

        private final Long memberId;
        private final String nickname;
        private final String profileImageUrl;
    }

    @Getter
    @Builder
    @ToString
    public static class FollowerInfo {

            private final Long memberId;
            private final String nickname;
            private final String profileImageUrl;
            private final boolean following;
    }



}
