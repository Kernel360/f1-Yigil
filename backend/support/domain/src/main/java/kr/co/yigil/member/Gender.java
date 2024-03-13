package kr.co.yigil.member;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender {
    MALE("male"),
    FEMALE("female"),
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
