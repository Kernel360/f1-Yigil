package kr.co.yigil.member;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Ages{
    NONE("없음"),
    UNDER_TEENAGERS("0"),
    TEENAGERS("10"),
    TWENTIES("20"),
    THIRTIES("30"),
    FORTIES("40"),
    FIFTIES("50"),
    OVER_SIXTIES("60");

    private final String viewName;

    @JsonCreator
    public static Ages from(String s) {

        for (Ages ages : Ages.values()) {
            if (ages.getViewName().equals(s)) {
                return ages;
            }
        }
        return Ages.NONE;
    }
}
