package kr.co.yigil.login.application.strategy;

import jakarta.servlet.http.HttpSession;
import kr.co.yigil.login.dto.request.LoginRequest;
import kr.co.yigil.login.dto.response.LoginResponse;
import kr.co.yigil.login.dto.response.GoogleTokenInfoResponse;
import kr.co.yigil.member.domain.Member;
import kr.co.yigil.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoogleLoginStrategy implements LoginStrategy{

    private static final String PROVIDER_NAME = "google";

    private final MemberRepository memberRepository;


    @Override
    public LoginResponse login(LoginRequest request, String accessToken, HttpSession session) {
        return new LoginResponse();
    }

    @Override
    public String getProviderName() {
        return "google";
    }

    private boolean isTokenValid(String accessToken, Long expectedUserId) {
        GoogleTokenInfoResponse tokenInfo = requestGoogleTokenInfo(accessToken);
        return isUserIdValid(tokenInfo, expectedUserId);
    }

    private GoogleTokenInfoResponse requestGoogleTokenInfo(String accessToken) {
        return null;
    }

    private boolean isUserIdValid(GoogleTokenInfoResponse tokenInfo, Long expectedUserId) {
        return false;
    }

    private Member registerNewMember(LoginRequest request) {
        return null;
    }
}
