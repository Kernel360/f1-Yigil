package kr.co.yigil.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode {

    INVALID_REQUEST(1000, "올바르지 않은 요청입니다."),

    NOT_FOUND_MEMBER_ID(1001, "사용자를 찾을 수 없습니다."),

    EMPTY_FILE(5001, "업로드한 파일이 비어있습니다."),
    INVALID_FILE_TYPE(5002, "지원하지 않는 형식의 파일입니다."),
    EXCEED_FILE_CAPACITY(5003, "업로드 가능한 파일 용량을 초과했습니다."),

    INVALID_ACCESS_TOKEN(9101, "올바르지 않은 형식의 Access Token입니다."),
    INVALID_AUTHORITY(9201, "해당 요청에 대한 접근 권한이 없습니다."),

    INTERNAL_SERVER_ERROR(9999, "서버 에러가 발생했습니다.");

    private final int code;
    private final String message;
}
