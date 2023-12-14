package kr.co.yigil.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode {

    INVALID_REQUEST(1000, "올바르지 않은 요청입니다."),

    INVALID_ACCESS_TOKEN(9101, "올바르지 않은 형식의 Access Token입니다.");

    private final int code;
    private final String message;
}
