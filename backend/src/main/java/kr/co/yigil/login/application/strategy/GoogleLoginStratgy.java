package kr.co.yigil.login.application.strategy;

import kr.co.yigil.login.dto.request.LoginRequest;
import kr.co.yigil.login.dto.response.GoogleLoginResponse;
import kr.co.yigil.login.dto.response.LoginResponse;

public class GoogleLoginStratgy implements LoginStrategy{

    @Override
    public LoginResponse login(LoginRequest request, String accessToken) {
        return new GoogleLoginResponse();
    }

    @Override
    public String getProviderName() {
        return "google";
    }
}
