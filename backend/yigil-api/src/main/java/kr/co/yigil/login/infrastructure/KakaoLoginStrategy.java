package kr.co.yigil.login.infrastructure;

import static kr.co.yigil.global.exception.ExceptionCode.INVALID_ACCESS_TOKEN;

import java.util.Collections;
import kr.co.yigil.global.exception.InvalidTokenException;
import kr.co.yigil.login.domain.LoginCommand;
import kr.co.yigil.login.interfaces.dto.response.KakaoTokenInfoResponse;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.SocialLoginType;
import kr.co.yigil.member.domain.MemberReader;
import kr.co.yigil.member.domain.MemberStore;
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
    private final MemberReader memberReader;

    private final MemberStore memberStore;

    private final static String PROVIDER_NAME = "kakao";

    @Value("${kakao.token.info.url}")
    private String KAKAO_TOKEN_INFO_URL;

    @Setter
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public Long processLogin(LoginCommand.LoginRequest loginCommand, String accessToken) {

        if(!isTokenValid(accessToken, loginCommand.getId())) {
            throw new InvalidTokenException(INVALID_ACCESS_TOKEN);
        }

        Member member = memberReader.findMemberBySocialLoginIdAndSocialLoginType(
                        loginCommand.getId().toString(), SocialLoginType.KAKAO)
                .orElseGet(() -> registerNewMember(loginCommand));

        return member.getId();
    }

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

    private Member registerNewMember(LoginCommand.LoginRequest loginCommand) {
        Member newMember = loginCommand.toEntity(PROVIDER_NAME);
        return memberStore.save(newMember);
    }

}