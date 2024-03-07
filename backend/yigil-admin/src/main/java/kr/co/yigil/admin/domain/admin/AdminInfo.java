package kr.co.yigil.admin.domain.admin;

import java.util.Optional;
import kr.co.yigil.admin.domain.Admin;
import lombok.Getter;
import lombok.ToString;
import kr.co.yigil.file.AttachFile;

public class AdminInfo {

    @Getter
    @ToString
    public static class AdminInfoResponse {
        private final String nickname;
        private final String profileUrl;

        public AdminInfoResponse(Admin admin) {
            nickname = admin.getNickname();
            profileUrl = Optional.ofNullable(admin.getProfileImage())
                    .map(AttachFile::getFileUrl)
                    .orElse("");
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
            profileUrl = Optional.ofNullable(admin.getProfileImage())
                    .map(AttachFile::getFileUrl)
                    .orElse("");
            email = admin.getEmail();
            password = admin.getPassword();
        }

    }

}
