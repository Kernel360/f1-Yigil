package kr.co.yigil.global.exception;

import lombok.Getter;

@Getter
public class MailException extends RuntimeException{

    private final int code;
    private final String message;

    public MailException(final ExceptionCode exceptionCode) {
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
    }
}
