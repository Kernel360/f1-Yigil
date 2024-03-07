package kr.co.yigil.admin.domain.adminSignUp;

import kr.co.yigil.admin.domain.AdminSignUp;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class AdminSignUpCommand {

    @Getter
    @Builder
    @ToString
    public static class AdminSignUpRequest {

        private String email;
        private String nickname;

        public AdminSignUp toEntity() {
            return new AdminSignUp(email, nickname);
        }

    }
}
