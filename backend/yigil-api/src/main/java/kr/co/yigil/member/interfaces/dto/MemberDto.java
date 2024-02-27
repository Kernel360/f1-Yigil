package kr.co.yigil.member.interfaces.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

public class MemberDto {

    @Getter
    @Setter
    @ToString
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberUpdateRequest {

        private String nickname;

        private String ages;

        private String gender;

        private MultipartFile profileImageFile;
    }

//    @Getter
//    @Builder
//    @ToString
//    @AllArgsConstructor
//    public static class TravelsVisibilityRequest {
//
//        private List<Long> travelIds;
//        private Boolean isPrivate;
//    }

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
    public static class MemberUpdateResponse {

        private final String message;
    }

    @Getter
    @Builder
    @ToString
    public static class FollowerResponse {

        private final List<FollowInfo> content;
        private final boolean hasNext;
    }

    @Getter
    @Builder
    @ToString
    public static class FollowingResponse {

        private final List<FollowInfo> content;
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
