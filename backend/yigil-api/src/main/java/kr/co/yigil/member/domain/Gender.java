package kr.co.yigil.member.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import kr.co.yigil.member.Ages;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender {
    MALE("남성"),
    FEMALE("여성"),
    NONE("없음");

    private final String viewName;
    @JsonCreator
    public static Gender from(String s) {
        for (Gender gender : Gender.values()) {
            if (gender.getViewName().equals(s)) {
                return gender;
            }
        }
        return Gender.NONE;
    }
}
