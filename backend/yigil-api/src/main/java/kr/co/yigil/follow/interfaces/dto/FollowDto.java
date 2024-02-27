package kr.co.yigil.follow.interfaces.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class FollowDto {

    @Getter
    @Builder
    @ToString
    public static class FollowerResponse {

        private final List<FollowBasicInfo> content;
        private final boolean hasNext;
    }

    @Getter
    @Builder
    @ToString
    public static class FollowingResponse {

        private final List<FollowBasicInfo> content;
        private final boolean hasNext;
    }

    @Getter
    @Builder
    @ToString
    public static class FollowBasicInfo {

        private final Long memberId;
        private final String nickname;
        private final String profileImageUrl;
    }

}
