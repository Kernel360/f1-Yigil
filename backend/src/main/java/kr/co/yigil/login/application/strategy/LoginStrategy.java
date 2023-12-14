package kr.co.yigil.login.application.strategy;

import kr.co.yigil.login.dto.request.LoginRequest;
import kr.co.yigil.login.dto.response.LoginResponse;

public interface LoginStrategy {
    LoginResponse login(LoginRequest request);
    String getProviderName();
}
