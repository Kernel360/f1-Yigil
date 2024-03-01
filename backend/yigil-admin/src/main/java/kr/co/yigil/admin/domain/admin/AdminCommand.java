package kr.co.yigil.admin.domain.admin;

import kr.co.yigil.admin.domain.adminSignUp.AdminSignUp;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class AdminCommand {
    @Getter
    @Builder
    @ToString
    public static class LoginRequest {

        private String email;
        private String password;

    }


}
