package kr.co.yigil.login.application;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.co.yigil.auth.Auth;
import kr.co.yigil.auth.domain.Accessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;

public class LoginArgumentResolverTest {
    private LoginArgumentResolver resolver;
    private NativeWebRequest webRequest;
    private HttpServletRequest request;
    private HttpSession session;
    private MethodParameter methodParameter;

    @BeforeEach
    public void setUp() {
        resolver = new LoginArgumentResolver();
        webRequest = mock(NativeWebRequest.class);
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);
        methodParameter = mock(MethodParameter.class);

        when(webRequest.getNativeRequest()).thenReturn(request);
        when(request.getSession(false)).thenReturn(session);
        when(methodParameter.withContainingClass(Long.class)).thenReturn(methodParameter);
        when(methodParameter.hasParameterAnnotation(Auth.class)).thenReturn(true);
    }

    @DisplayName("세션이 없을 때 guest임을 잘 반환하는지")
    @Test
    void shouldReturnGuestWhenNoSession() throws Exception {
        when(request.getSession(false)).thenReturn(null);

        Object result = resolver.resolveArgument(methodParameter, null, webRequest, null);
        assertTrue(result instanceof Accessor && !((Accessor) result).isMember());
    }

    @DisplayName("세션에 MemberId가 없을 때 guest임을 잘 반환하는지")
    @Test
    void shouldReturnMemberGuestWhenNoMemberIdInSession() throws Exception {
        when(session.getAttribute("memberId")).thenReturn(null);

        Object result = resolver.resolveArgument(methodParameter, null, webRequest, null);
        assertTrue(result instanceof Accessor && !((Accessor) result).isMember());
    }

    @DisplayName("세션에 MemberId가 있을 때 member임을 잘 반환하는지")
    @Test
    void shouldReturnMemberWhenMemberIdInSession() throws Exception {
        Long memberId = 1L;
        when(session.getAttribute("memberId")).thenReturn(memberId);

        Object result = resolver.resolveArgument(methodParameter, null, webRequest, null);
        assertTrue(result instanceof Accessor && ((Accessor) result).isMember() && ((Accessor) result).getMemberId().equals(memberId));
    }

}

