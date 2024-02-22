package kr.co.yigil.member.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

public class MemberCommand {

    @Getter
    @Builder
    @ToString
    public static class RegisterMemberRequest {
        private final String email;
        private final String password;
        private final String nickname;
        private final String profileImageUrl;

        public RegisterMemberRequest(String email, String password, String nickname, String profileImageUrl) {
            this.email = email;
            this.password = password;
            this.nickname = nickname;
            this.profileImageUrl = profileImageUrl;
        }

    }

    @Getter
    @Builder
    @ToString
    public static class MemberUpdateRequest {
        private String nickname;
        private MultipartFile profileImageFile;
        private String ages;
        private String gender;
    }

}
