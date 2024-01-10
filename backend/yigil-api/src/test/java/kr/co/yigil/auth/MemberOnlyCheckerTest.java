package kr.co.yigil.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.when;

import kr.co.yigil.auth.MemberOnlyChecker;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.global.exception.AuthException;
import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MemberOnlyCheckerTest {
    private MemberOnlyChecker memberOnlyChecker;

    @Mock
    private JoinPoint joinPoint;

    @Mock
    private Accessor accessor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        memberOnlyChecker = new MemberOnlyChecker();
    }

    @DisplayName("로그인이 안되었을 떄 AuthExcetpion이 잘 발생하는지")
    @Test
    void shouldThrowAuthExceptionWhenAccessorIsNotMember() {
        when(accessor.isMember()).thenReturn(false);
        Object[] args = new Object[]{accessor};
        when(joinPoint.getArgs()).thenReturn(args);

        AuthException e = Assertions.assertThrows(AuthException.class, () -> memberOnlyChecker.check(joinPoint));

        assertThat(e.getCode()).isEqualTo(9201);
        assertThat(e.getMessage()).isEqualTo("해당 요청에 대한 접근 권한이 없습니다.");
    }

    @DisplayName("로그인이 되었을 때 Exception이 발생하지 않는지")
    @Test
    void shouldNotThrowExceptionWhenAccessorIsMember() {
        when(accessor.isMember()).thenReturn(true);
        Object[] args = new Object[]{accessor};
        when(joinPoint.getArgs()).thenReturn(args);

    }

}