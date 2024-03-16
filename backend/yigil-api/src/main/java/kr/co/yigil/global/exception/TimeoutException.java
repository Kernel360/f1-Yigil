package kr.co.yigil.global.exception;

import lombok.Getter;

@Getter
public class TimeoutException extends RuntimeException{

    private final int code;
    private final String message;

    public TimeoutException(final ExceptionCode exceptionCode) {
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
    }
}
