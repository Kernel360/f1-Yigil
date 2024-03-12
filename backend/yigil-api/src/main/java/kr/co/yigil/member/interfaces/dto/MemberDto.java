package kr.co.yigil.member.interfaces.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
        private List<Long> favoriteRegionIds;
    }

    @Getter
    @Builder
    @ToString
    public static class Main {

        private final Long memberId;
        private final String email;
        private final String nickname;
        private final String profileImageUrl;
        private final String age;
        private final String gender;
        private final List<FavoriteRegion> favoriteRegions;
        private final int followingCount;
        private final int followerCount;
    }

    @Getter
    @Builder
    public static class FavoriteRegion {

        private final Long id;
        private final String name;
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
    public static class MemberDeleteResponse {
        private final String message;
    }

    @Getter
    @Builder
    public static class NicknameCheckResponse {
        private final boolean available;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NicknameCheckRequest {
        private String nickname;
    }
}