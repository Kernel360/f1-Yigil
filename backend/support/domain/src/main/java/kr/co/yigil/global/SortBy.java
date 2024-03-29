package kr.co.yigil.global;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SortBy {
    CREATED_AT("createdAt"),
    RATE("rate"),
    TITLE("title"),
    ID("id"),
    PLACE_NAME("place.name"),
    NAME("name"),
    NICKNAME("nickname"),
    LATEST_UPLOADED_TIME("latestUploadedTime"),
    FOLLOWER_NAME("follower.nickname"),
    FOLLOWING_NAME("following.nickname")
    ;

    private String value;
    SortBy(String value) {
        this.value = value;
    }

}
