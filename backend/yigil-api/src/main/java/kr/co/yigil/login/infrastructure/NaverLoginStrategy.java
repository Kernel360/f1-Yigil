package kr.co.yigil.login.infrastructure;

import static kr.co.yigil.global.exception.ExceptionCode.INVALID_ACCESS_TOKEN;

import java.util.Collections;
import kr.co.yigil.global.exception.InvalidTokenException;
import kr.co.yigil.login.domain.LoginCommand;
import kr.co.yigil.login.domain.LoginCommand.LoginRequest;
import kr.co.yigil.login.interfaces.dto.response.NaverTokenInfoResponse;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.SocialLoginType;
import kr.co.yigil.member.domain.MemberReader;
import kr.co.yigil.member.domain.MemberStore;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
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
@PropertySource("classpath:url.properties")
public class NaverLoginStrategy implements LoginStrategy {
    private final MemberReader memberReader;
    private final MemberStore memberStore;
    private final static String PROVIDER_NAME = "naver";

    @Setter
    private RestTemplate restTemplate = new RestTemplate();

    @Value("${naver.token.info.url}")
    private String NAVER_TOKEN_INFO_URL;

    @Override
    public Long processLogin(LoginRequest loginCommand, String accessToken) {
        if(!isTokenValid(accessToken, loginCommand.getId())) {
            throw new InvalidTokenException(INVALID_ACCESS_TOKEN);
        }

        Member member = memberReader.findMemberByEmailAndSocialLoginType(
                        loginCommand.getEmail(), SocialLoginType.NAVER)
                .orElseGet(() -> registerNewMember(loginCommand));

        return member.getId();
    }

    @Override
    public String getProviderName() {
        return PROVIDER_NAME;
    }

    private boolean isTokenValid(String accessToken, String expectedUserEmail) {
        NaverTokenInfoResponse tokenInfo = requestNaverTokenInfo(accessToken);
        return isUserIdValid(tokenInfo, expectedUserEmail);
    }

    private NaverTokenInfoResponse requestNaverTokenInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<NaverTokenInfoResponse> response = restTemplate.exchange(
                    NAVER_TOKEN_INFO_URL,
                    HttpMethod.GET,
                    entity,
                    NaverTokenInfoResponse.class);

            if (response.getStatusCode().is4xxClientError()) {
                throw new InvalidTokenException(INVALID_ACCESS_TOKEN);
            }
            return response.getBody();
        } catch (Exception e) {
            throw new InvalidTokenException(INVALID_ACCESS_TOKEN);
        }

    }

    private boolean isUserIdValid(NaverTokenInfoResponse tokenInfo, String expectedUserId) {
        return tokenInfo != null && tokenInfo.getResponse().getId().equals(expectedUserId);
    }

    private Member registerNewMember(LoginCommand.LoginRequest loginCommand) {
        Member newMember = loginCommand.toEntity(PROVIDER_NAME);
        return memberStore.save(newMember);
    }

}
