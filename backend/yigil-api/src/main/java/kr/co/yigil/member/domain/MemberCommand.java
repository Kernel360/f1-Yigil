package kr.co.yigil.member.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

public class MemberCommand {

    @Getter
    @Builder
    @ToString
    public static class MemberUpdateRequest {

        private String nickname;
        private String ages;
        private String gender;
        private MultipartFile profileImageFile;
    }

}
