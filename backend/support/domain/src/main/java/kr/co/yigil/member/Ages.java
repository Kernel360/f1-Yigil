package kr.co.yigil.member;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Ages{
    NONE("없음", "없음"),
    UNDER_TEENAGERS("10대 이하", "0"),
    TEENAGERS("10대", "10"),
    TWENTIES("20대", "20"),
    THIRTIES("30대", "30"),
    FORTIES("40대", "40"),
    FIFTIES("50대", "50"),
    OVER_SIXTIES("60대 이상", "60");

    private final String viewName;
    private final String input;
    @JsonCreator
    public static Ages from(String s) {

        for (Ages ages : Ages.values()) {
            if (ages.getInput().equals(s)) {
                return ages;
            }
        }
        return Ages.NONE;
    }
}
