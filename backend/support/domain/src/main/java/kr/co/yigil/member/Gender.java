package kr.co.yigil.member;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender {
    MALE("남성", "male"),
    FEMALE("여성", "female"),
    NONE("없음", "none");

    private final String viewName;
    private final String input;
    @JsonCreator
    public static Gender from(String s) {
        for (Gender gender : Gender.values()) {
            if (gender.getInput().equals(s)) {
                return gender;
            }
        }
        return Gender.NONE;
    }
}
