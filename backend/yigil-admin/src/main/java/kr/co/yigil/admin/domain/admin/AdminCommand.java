package kr.co.yigil.admin.domain.admin;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

public class AdminCommand {
    @Getter
    @Builder
    @ToString
    public static class LoginRequest {

        private String email;
        private String password;

    }

    @Getter
    @Builder
    @ToString
    public static class AdminUpdateRequest {
        private String nickname;
        private MultipartFile profileImageFile;
        private String password;

    }

    @Getter
    @Builder
    @ToString
    public static class AdminPasswordUpdateRequest {
        private String existingPassword;
        private String newPassword;

    }


}
