package kr.co.yigil.login.infrastructure;

import static kr.co.yigil.global.exception.ExceptionCode.INVALID_ACCESS_TOKEN;

import java.util.Collections;
import kr.co.yigil.global.exception.InvalidTokenException;
import kr.co.yigil.login.domain.LoginCommand;
import kr.co.yigil.login.interfaces.dto.response.GoogleTokenInfoResponse;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.SocialLoginType;
import kr.co.yigil.member.domain.MemberReader;
import kr.co.yigil.member.domain.MemberStore;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
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
public class GoogleLoginStrategy implements LoginStrategy {
    private final MemberReader memberReader;

    private final MemberStore memberStore;

    private static final String PROVIDER_NAME = "google";


    @Setter
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public Long processLogin(LoginCommand.LoginRequest loginCommand, String accessToken) {

        if(!isTokenValid(accessToken, loginCommand.getId())) {
            throw new InvalidTokenException(INVALID_ACCESS_TOKEN);
        }

        Member member = memberReader.findMemberBySocialLoginIdAndSocialLoginType(
                        loginCommand.getId().toString(), SocialLoginType.GOOGLE)
                .orElseGet(() -> registerNewMember(loginCommand));

        return member.getId();
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
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<GoogleTokenInfoResponse> response = restTemplate.exchange(
                    "https://oauth2.googleapis.com/tokeninfo?access_token={accessToken}",
                    HttpMethod.GET,
                    entity,
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

    private Member registerNewMember(LoginCommand.LoginRequest loginCommand) {
        Member newMember = loginCommand.toEntity(PROVIDER_NAME);
        return memberStore.save(newMember);
    }
}
