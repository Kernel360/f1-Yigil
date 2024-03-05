package kr.co.yigil.admin.domain.admin;

import lombok.Getter;
import lombok.ToString;

public class AdminInfo {

    @Getter
    @ToString
    public static class AdminInfoResponse {
        private final String nickname;
        private final String profileUrl;

        public AdminInfoResponse(Admin admin) {
            nickname = admin.getNickname();
            profileUrl = admin.getProfileImageUrl();
        }

    }

    @Getter
    @ToString
    public static class AdminDetailInfoResponse {
        private final String nickname;
        private final String profileUrl;
        private final String email;
        private final String password;

        public AdminDetailInfoResponse(Admin admin) {
            nickname = admin.getNickname();
            profileUrl = admin.getProfileImageUrl();
            email = admin.getEmail();
            password = admin.getPassword();
        }

    }

}
