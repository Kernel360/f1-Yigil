package kr.co.yigil.member.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Ages {
    NONE("없음"),
    UNDER_TEENAGERS("10대 이하"),
    TEENAGERS("10대"),
    TWENTIES("20대"),
    THIRTIES("30대"),
    FORTIES("40대"),
    FIFTIES("50대"),
    OVER_SIXTIES("60대 이상");

    private final String viewName;
    @JsonCreator
    public static kr.co.yigil.member.Ages from(String s) {
        for (kr.co.yigil.member.Ages ages : kr.co.yigil.member.Ages.values()) {
            if (ages.getViewName().equals(s)) {
                return ages;
            }
        }
        return kr.co.yigil.member.Ages.NONE;
    }
}
