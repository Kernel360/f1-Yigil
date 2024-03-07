package kr.co.yigil.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode {
  
    INVALID_REQUEST(1000, "올바르지 않은 요청입니다."),
    NOT_FOUND_MEMBER_ID(1001, "사용자를 찾을 수 없습니다."),
    NOT_FOUND_SPOT_ID(1021, "해당하는 spot이 없습니다"),
    NOT_FOUND_COURSE_ID(1031, "해당하는 course가 없습니다"),
    NOT_FOUND_TRAVEL_ID(1041, "해당하는 travel이 없습니다"),
    NOT_FOUND_COMMENT_ID(1051, "해당하는 comment가 없습니다"),
    NOT_FOUND_PLACE_ID(1061, "해당하는 place가 없습니다"),
    NOT_FOUND_REGION_ID(1081, "해당하는 region이 없습니다"),
    ADMIN_NOT_FOUND(1101, "관리자 정보를 찾을 수 없습니다."),
    ADMIN_PASSWORD_DOES_NOT_MATCH(1102, "비밀번호가 맞지 않습니다."),
    ADMIN_ALREADY_EXISTED(1201, "이미 존재하는 이메일 또는 닉네임입니다."),
    ADMIN_SIGNUP_REQUEST_NOT_FOUND(1202, "관리자 가입 요청 정보를 찾을 수 없습니다."),


    NOTICE_NOT_FOUND(3001, "공지사항을 찾을 수 없습니다."),

    EMPTY_FILE(5001, "업로드한 파일이 비어있습니다."),
    INVALID_FILE_TYPE(5002, "지원하지 않는 형식의 파일입니다."),
    EXCEED_FILE_CAPACITY(5003, "업로드 가능한 파일 용량을 초과했습니다."),

    INVALID_JWT_TOKEN(9101, "올바르지 않은 형식의 JWT 토큰입니다."),
    EXPIRED_JWT_TOKEN(9102, "만료된 JWT 토큰입니다."),
    INVALID_AUTHORITY(9201, "해당 요청에 대한 접근 권한이 없습니다."),

    SMTP_SERVER_ERROR(9998, "이메일 전송 중 에러가 발생했습니다."),
    INTERNAL_SERVER_ERROR(9999, "서버 에러가 발생했습니다.")
    ;

    private final int code;
    private final String message;
}
