package kr.co.yigil.login.application.strategy;

import kr.co.yigil.login.dto.request.LoginRequest;
import kr.co.yigil.login.dto.response.LoginResponse;

public class KakaoLoginStrategy implements LoginStrategy {

    private final String PROVIDER_NAME = "kakao";

    @Override
    public LoginResponse login(LoginRequest request) {

    }

    @Override
    public String getProviderName() {
        return PROVIDER_NAME;
    }
}
