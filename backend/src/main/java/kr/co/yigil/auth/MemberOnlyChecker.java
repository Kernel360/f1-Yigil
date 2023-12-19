package kr.co.yigil.auth;

import static kr.co.yigil.global.exception.ExceptionCode.INVALID_AUTHORITY;

import java.util.Arrays;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.global.exception.AuthException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MemberOnlyChecker {
    @Before("@annotation(kr.co.yigil.auth.MemberOnly)")
    public void check(final JoinPoint joinPoint) {
        Arrays.stream(joinPoint.getArgs())
                .filter(Accessor.class::isInstance)
                .map(Accessor.class::cast)
                .filter(Accessor::isMember)
                .findFirst()
                .orElseThrow(() -> new AuthException(INVALID_AUTHORITY));
    }
}
