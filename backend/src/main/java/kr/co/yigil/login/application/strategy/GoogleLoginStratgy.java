package kr.co.yigil.login.application.strategy;

import kr.co.yigil.login.dto.request.LoginRequest;
import kr.co.yigil.login.dto.response.LoginResponse;

public class GoogleLoginStratgy implements LoginStrategy{

    @Override
    public LoginResponse login(LoginRequest request) {
        //r
    }

    @Override
    public String getProviderName() {
        return "google";
    }
}
