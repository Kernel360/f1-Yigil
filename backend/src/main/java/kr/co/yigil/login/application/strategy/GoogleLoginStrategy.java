package kr.co.yigil.login.application.strategy;

import jakarta.servlet.http.HttpSession;
import kr.co.yigil.login.dto.request.LoginRequest;
import kr.co.yigil.login.dto.response.LoginResponse;

public class GoogleLoginStrategy implements LoginStrategy{

    @Override
    public LoginResponse login(LoginRequest request, String accessToken, HttpSession session) {
        return new LoginResponse();
    }

    @Override
    public String getProviderName() {
        return "google";
    }
}
