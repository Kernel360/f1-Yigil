package kr.co.yigil.login.application.strategy;

import static kr.co.yigil.global.exception.ExceptionCode.INVALID_ACCESS_TOKEN;

import jakarta.servlet.http.HttpSession;
import java.util.Collections;
import kr.co.yigil.global.exception.InvalidTokenException;
import kr.co.yigil.login.dto.request.LoginRequest;
import kr.co.yigil.login.dto.response.KakaoTokenInfoResponse;
import kr.co.yigil.login.dto.response.LoginResponse;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.SocialLoginType;
import kr.co.yigil.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
@PropertySource("classpath:url.properties")
public class KakaoLoginStrategy implements LoginStrategy {

    private final static String PROVIDER_NAME = "kakao";

    @Value("${kakao.token.info.url}")
    private String KAKAO_TOKEN_INFO_URL;

    private final MemberRepository memberRepository;

    @Setter
    private RestTemplate restTemplate = new RestTemplate();

//
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

      //
    @Override
    public String getProviderName() {
        return PROVIDER_NAME;
    }

    private boolean isTokenValid(String accessToken, Long expectedUserId) {
        KakaoTokenInfoResponse tokenInfo = requestKakaoTokenInfo(accessToken);
        return isUserIdValid(tokenInfo, expectedUserId);
    }

    private KakaoTokenInfoResponse requestKakaoTokenInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<KakaoTokenInfoResponse> response = restTemplate.exchange(
                    KAKAO_TOKEN_INFO_URL, HttpMethod.GET, entity, KakaoTokenInfoResponse.class
            );

            if(response.getStatusCode().is4xxClientError()) {
                throw new InvalidTokenException(INVALID_ACCESS_TOKEN);
            }

            return response.getBody();
        } catch (Exception e) {
            throw new InvalidTokenException(INVALID_ACCESS_TOKEN);
        }
    }

    private boolean isUserIdValid(KakaoTokenInfoResponse tokenInfo, Long expectedUserId) {
        return tokenInfo != null && tokenInfo.getId().equals(expectedUserId);
    }

    private Member registerNewMember(LoginRequest request) {
        Member newMember = new Member(request.getEmail(), request.getId().toString(), request.getNickname(), request.getProfileImageUrl(), PROVIDER_NAME);
        return memberRepository.save(newMember);
    }

}