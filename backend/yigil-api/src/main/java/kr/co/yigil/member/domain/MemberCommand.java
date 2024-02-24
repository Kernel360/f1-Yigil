package kr.co.yigil.member.domain;

import java.util.List;
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

    @Getter
    @Builder
    @ToString
    public static class CoursesVisibilityRequest {

        private List<Long> courseIds;
        private Boolean isPrivate;
    }

    @Getter
    @Builder
    @ToString
    public static class SpotsVisibilityRequest {

        private List<Long> spotIds;
        private Boolean isPrivate;
    }
}
