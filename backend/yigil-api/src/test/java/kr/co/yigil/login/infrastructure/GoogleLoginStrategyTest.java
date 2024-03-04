package kr.co.yigil.login.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.GET;

import java.util.Optional;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.global.exception.InvalidTokenException;
import kr.co.yigil.login.domain.LoginCommand;
import kr.co.yigil.login.interfaces.dto.response.GoogleTokenInfoResponse;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.SocialLoginType;
import kr.co.yigil.member.domain.MemberReader;
import kr.co.yigil.member.domain.MemberStore;
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
@SpringBootTest(classes = GoogleLoginStrategy.class)
public class GoogleLoginStrategyTest {

    @MockBean
    private MemberStore memberStore;

    @MockBean
    private MemberReader memberReader;

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private GoogleLoginStrategy googleLoginStrategy;

    @BeforeEach
    void setUp() {
        googleLoginStrategy.setRestTemplate(restTemplate);
    }

    @DisplayName("토큰이 유효하고 멤버가 존재하면 로그인이 잘 되는지")
    @Test
    void whenTokenIsValid_andMemberExists_thenLoginSuccessful() {
        GoogleTokenInfoResponse mockResponse = new GoogleTokenInfoResponse("email@example.com", 50000);

        String accessToken = "mockAccessToken";

        when(restTemplate.exchange(
                anyString(),
                eq(GET),
                any(HttpEntity.class),
                eq(GoogleTokenInfoResponse.class),
                eq(accessToken)
        )).thenReturn(ResponseEntity.ok(mockResponse));

        Long memberId = 1L;
        Member mockMember = new Member(memberId,"email@example.com", "12345678", "user", "image_url", SocialLoginType.GOOGLE);

        when(memberReader.findMemberBySocialLoginIdAndSocialLoginType("12345678", SocialLoginType.GOOGLE)).thenReturn(
                Optional.of(mockMember));

        LoginCommand.LoginRequest loginCommand = mock(LoginCommand.LoginRequest.class);
        when(loginCommand.getEmail()).thenReturn("email@example.com");
        when(loginCommand.getId()).thenReturn("12345678");

        Long response = googleLoginStrategy.processLogin(loginCommand, accessToken);

        assertThat(response).isEqualTo(memberId);
    }

    @DisplayName("토큰이 유효하지 않은 경우 예외가 잘 발생하는지")
    @Test
    void whenTokenIsInvalid_thenThrowsException() {
        String accessToken = "mockAccessToken";

        when(restTemplate.exchange(
                anyString(),
                eq(GET),
                any(HttpEntity.class),
                eq(GoogleTokenInfoResponse.class),
                eq(accessToken)
        )).thenThrow(new InvalidTokenException(ExceptionCode.INVALID_ACCESS_TOKEN));

        LoginCommand.LoginRequest loginCommand = mock(LoginCommand.LoginRequest.class);

        Throwable thrown = catchThrowable(() -> googleLoginStrategy.processLogin(loginCommand, accessToken));

        assertThat(thrown).isInstanceOf(InvalidTokenException.class);
    }

    @DisplayName("새로운 사용자가 로그인을 요청할 경우 회원 가입이 잘 되는지")
    @Test
    void whenMemberIsNew_thenRegisterNewMember() {
        GoogleTokenInfoResponse mockResponse = new GoogleTokenInfoResponse();
        mockResponse.setEmail("test@test.com");
        String accessToken = "mockAccessToken";

        when(restTemplate.exchange(
                anyString(),
                eq(GET),
                any(HttpEntity.class),
                eq(GoogleTokenInfoResponse.class),
                eq(accessToken)
        )).thenReturn(ResponseEntity.ok(mockResponse));

        LoginCommand.LoginRequest loginCommand = mock(LoginCommand.LoginRequest.class);
        when(loginCommand.getId()).thenReturn("12345678");
        when(loginCommand.getEmail()).thenReturn("test@test.com");

        when(memberReader.findMemberBySocialLoginIdAndSocialLoginType("12345678", SocialLoginType.GOOGLE)).thenReturn(Optional.empty());
        Long memberId = 1L;
        Member mockMember = new Member(memberId,"email@example.com", "12345678", "user", "image_url", SocialLoginType.GOOGLE);
        when(loginCommand.toEntity(anyString())).thenReturn(mockMember);
        when(memberStore.save(any(Member.class))).thenReturn(mockMember);

        Long response = googleLoginStrategy.processLogin(loginCommand, accessToken);

        assertThat(response).isEqualTo(memberId);

    }
}
