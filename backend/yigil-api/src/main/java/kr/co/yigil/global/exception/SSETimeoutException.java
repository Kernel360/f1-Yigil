package kr.co.yigil.global.exception;

import lombok.Getter;

@Getter
public class SSETimeoutException extends TimeoutException {

    public SSETimeoutException(final ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
