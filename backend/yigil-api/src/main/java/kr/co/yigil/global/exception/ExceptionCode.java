package kr.co.yigil.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode {
    INVALID_REQUEST(1000, "올바르지 않은 요청입니다."),
    NOT_FOUND_MEMBER_ID(1001, "사용자를 찾을 수 없습니다."),
    NOT_FOUND_POST_ID(1011, "해당하는 post가 없습니다"),
    NOT_FOUND_SPOT_ID(1021, "해당하는 spot이 없습니다"),
    NOT_FOUND_COURSE_ID(1031, "해당하는 course가 없습니다"),
    NOT_FOUND_TRAVEL_ID(1041, "해당하는 travel이 없습니다"),
    NOT_FOUND_COMMENT_ID(1051, "해당하는 comment가 없습니다"),
    NOT_FOUND_PLACE_ID(1061, "해당하는 place가 없습니다"),
    NOT_FOUND_FAVOR_COUNT(1071, "해당하는 favor count가 없습니다"),


    // travel 3000

    // spot 3100
    ALREADY_EXIST_SPOT(3001, "이미 등록된 spot입니다."),

    // course 3200

    FOLLOW_MYSELF(4001, "자신을 follow 할 수 없습니다."),
    ALREADY_FOLLOWING(4002, "이미 follow 중인 사용자입니다."),
    UNFOLLOW_MYSELF(4003, "자신을 unfollow 할 수 없습니다."),
    NOT_FOLLOWING(4004, "팔로우 중이 아닌 사용자입니다."),

    EMPTY_FILE(5001, "업로드한 파일이 비어있습니다."), // todo 현재  모듈 구조상 AttachFiles에서  사용 불가
    INVALID_FILE_TYPE(5002, "지원하지 않는 형식의 파일입니다."),
    EXCEED_FILE_CAPACITY(5003, "업로드 가능한 파일 용량을 초과했습니다."),
    EXCEED_FILE_COUNT(5004, "업로드 가능한 파일 개수를 초과했습니다."), // todo 현재  모듈 구조상 AttachFiles에서  사용 불가

    // GeoJson
    //    INVALID_GEOMETRY_TYPE(6001, "geometry 타입이 다릅니다"),
    INVALID_GEO_JSON_FORMAT(6002, "올바르지 않은 형식의 JSON String입니다."),
    GEO_JSON_CASTING_ERROR(6003, "JSON String Casting 오류가 발생했습니다."),
    INVALID_POINT_GEO_JSON(6011, "Point GeoJson 형식이 아닙니다"),
    INVALID_LINESTRING_GEO_JSON(6021, "Point GeoJson 형식이 아닙니다"),


    ALREADY_BOOKMARKED(7001, "이미 북마크된 장소입니다."),

    // Authorization & Authentication
    INVALID_ACCESS_TOKEN(9101, "올바르지 않은 형식의 Access Token입니다."),
    INVALID_AUTHORITY(9201, "해당 요청에 대한 접근 권한이 없습니다."),
    INTERNAL_SERVER_ERROR(9999, "서버 에러가 발생했습니다.");

    private final int code;
    private final String message;
}
