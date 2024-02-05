package kr.co.yigil.global.exception;

import lombok.Getter;

@Getter
public class InvalidTokenException extends AuthException {

    public InvalidTokenException(final ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
