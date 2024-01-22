package kr.co.yigil.login.application.strategy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.GET;


import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.global.exception.InvalidTokenException;
import kr.co.yigil.login.dto.request.LoginRequest;
import kr.co.yigil.login.dto.response.KakaoTokenInfoResponse;
import kr.co.yigil.login.dto.response.LoginResponse;
import kr.co.yigil.member.domain.Member;
import kr.co.yigil.member.domain.SocialLoginType;
import kr.co.yigil.member.domain.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = KakaoLoginStrategy.class)
public class KakaoLoginStrategyTest {

    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private KakaoLoginStrategy kakaoLoginStrategy;

    @BeforeEach
    void setUp() {
        kakaoLoginStrategy.setRestTemplate(restTemplate);
    }
    @DisplayName("토큰이 유효하고 멤버가 존재하면 로그인이 잘 되는지")
    @Test
    void whenTokenIsValid_andMemberExists_thenLoginSuccessful() {
        KakaoTokenInfoResponse mockResponse = new KakaoTokenInfoResponse();
        mockResponse.setId(12345678L);

        when(restTemplate.exchange(
                anyString(),
                eq(GET),
                any(HttpEntity.class),
                eq(KakaoTokenInfoResponse.class))
        ).thenReturn(ResponseEntity.ok(mockResponse));

        Member mockMember = new Member("email@example.com", "12345678", "user", "image_url", "kakao");

        when(memberRepository.findMemberBySocialLoginIdAndSocialLoginType("12345678", SocialLoginType.KAKAO)).thenReturn(Optional.of(mockMember));

        HttpSession mockSession = mock(HttpSession.class);

        LoginRequest loginRequest = new LoginRequest(12345678L, "user", "image_url", "email@example.com");

        LoginResponse response = kakaoLoginStrategy.login(loginRequest, "mockAccessToken", mockSession);

        assertThat(response.getMessage()).isEqualTo("로그인 성공");
    }

    @DisplayName("토큰이 유효하지 않은 경우 예외가 잘 발생하는지")
    @Test
    void whenTokenIsInvalid_thenThrowsException() {
        when(restTemplate.exchange(anyString(), eq(GET), any(HttpEntity.class), eq(KakaoTokenInfoResponse.class)))
                .thenThrow(new InvalidTokenException(ExceptionCode.INVALID_ACCESS_TOKEN));

        LoginRequest loginRequest = new LoginRequest(12345678L, "user", "image_url", "email@example.com");
        HttpSession mockSession = mock(HttpSession.class);

        Throwable thrown = catchThrowable(() -> kakaoLoginStrategy.login(loginRequest, "invalidAccessToken", mockSession));

        assertThat(thrown).isInstanceOf(InvalidTokenException.class);
    }

    @DisplayName("새로운 사용자가 로그인을 요청할 경우 회원 가입이 잘 되는지")
    @Test
    void whenMemberIsNew_thenRegisterNewMember() {
        KakaoTokenInfoResponse mockResponse = new KakaoTokenInfoResponse();
        mockResponse.setId(12345678L);
        when(restTemplate.exchange(anyString(), eq(GET), any(HttpEntity.class), eq(KakaoTokenInfoResponse.class)))
                .thenReturn(ResponseEntity.ok(mockResponse));

        when(memberRepository.findMemberBySocialLoginIdAndSocialLoginType("12345678", SocialLoginType.KAKAO)).thenReturn(Optional.empty());
        when(memberRepository.save(any(Member.class))).thenAnswer(invocation -> invocation.getArgument(0));

        HttpSession mockSession = mock(HttpSession.class);

        LoginRequest loginRequest = new LoginRequest(12345678L, "newUser", "new_image_url", "new_email@example.com");
        LoginResponse response = kakaoLoginStrategy.login(loginRequest, "mockAccessToken", mockSession);

        assertThat(response.getMessage()).isEqualTo("로그인 성공");
    }
}