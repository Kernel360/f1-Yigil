package kr.co.yigil.login.application.strategy;

import jakarta.servlet.http.HttpSession;
import kr.co.yigil.login.dto.request.LoginRequest;
import kr.co.yigil.login.dto.response.LoginResponse;

public interface LoginStrategy {
    LoginResponse login(LoginRequest request, String accessToken, HttpSession session);

    String getProviderName();

}
