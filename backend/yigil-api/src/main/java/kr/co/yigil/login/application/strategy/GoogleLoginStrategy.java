package kr.co.yigil.login.application.strategy;

import static kr.co.yigil.global.exception.ExceptionCode.INVALID_ACCESS_TOKEN;

import jakarta.servlet.http.HttpSession;
import kr.co.yigil.global.exception.InvalidTokenException;
import kr.co.yigil.login.dto.request.LoginRequest;
import kr.co.yigil.login.dto.response.LoginResponse;
import kr.co.yigil.login.dto.response.GoogleTokenInfoResponse;
import kr.co.yigil.member.domain.Member;
import kr.co.yigil.member.domain.SocialLoginType;
import kr.co.yigil.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoogleLoginStrategy implements LoginStrategy{

    private static final String PROVIDER_NAME = "google";

    private final MemberRepository memberRepository;

    @Setter
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public LoginResponse login(LoginRequest request, String accessToken, HttpSession session) {

        if(!isTokenValid(accessToken, request.getId())) {
            throw new InvalidTokenException(INVALID_ACCESS_TOKEN);
        }

        Member member = memberRepository.findMemberBySocialLoginIdAndSocialLoginType(request.getId().toString(),
                        SocialLoginType.valueOf(PROVIDER_NAME.toUpperCase()))
                .orElseGet(() -> registerNewMember(request));

        session.setAttribute("memberId", member.getId());
        return new LoginResponse("로그인 성공");
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
        try {
            ResponseEntity<GoogleTokenInfoResponse> response = restTemplate.exchange(
                    "https://oauth2.googleapis.com/tokeninfo?access_token={accessToken}",
                    HttpMethod.GET,
                    null,
                    GoogleTokenInfoResponse.class,
                    accessToken
            );

            if(response.getStatusCode().is4xxClientError()) {
                throw new InvalidTokenException(INVALID_ACCESS_TOKEN);
            }

            return response.getBody();
        } catch (Exception e) {
            throw new InvalidTokenException(INVALID_ACCESS_TOKEN);
        }
    }

    private boolean isUserIdValid(GoogleTokenInfoResponse tokenInfo, Long expectedUserId) {
        return tokenInfo != null && tokenInfo.getUserId().equals(expectedUserId);
    }

    private Member registerNewMember(LoginRequest request) {
        Member newMember = new Member(request.getEmail(), request.getId().toString(), request.getNickname(), request.getProfileImageUrl(), PROVIDER_NAME);
        return memberRepository.save(newMember);
    }
}
