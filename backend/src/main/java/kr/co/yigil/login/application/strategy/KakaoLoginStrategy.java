package kr.co.yigil.login.application.strategy;

import static kr.co.yigil.global.exception.ExceptionCode.INVALID_ACCESS_TOKEN;

import kr.co.yigil.global.exception.InvalidTokenException;
import kr.co.yigil.login.dto.request.KakaoLoginRequest;
import kr.co.yigil.login.dto.request.LoginRequest;
import kr.co.yigil.login.dto.response.KakaoLoginResponse;
import kr.co.yigil.login.dto.response.LoginResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:url.properties")
public class KakaoLoginStrategy implements LoginStrategy {

    private final String PROVIDER_NAME = "kakao";

    @Value("${Kakao-Token-Info-Url}")
    private String KAKAO_TOKEN_INFO_URL;

    @Override
    public LoginResponse login(LoginRequest request, String accessToken) {
        KakaoLoginRequest loginRequest = (KakaoLoginRequest) request;

        if(!isTokenValid(accessToken)) {
            throw new InvalidTokenException(INVALID_ACCESS_TOKEN);
        }
        return new KakaoLoginResponse();
    }

    @Override
    public String getProviderName() {
        return PROVIDER_NAME;
    }

    private boolean isTokenValid(String accessToken) {
        return true;
    }

}
