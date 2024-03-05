package kr.co.yigil.admin.domain.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

public class AdminInfo {

    @Getter
    @ToString
    public static class AdminInfoResponse {
        private final String username;
        private final String profileUrl;

        public AdminInfoResponse(Admin admin) {
            username = admin.getUsername();
            profileUrl = admin.getProfileImageUrl();
        }

    }

}
