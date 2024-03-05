package kr.co.yigil.global;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Selected {
    ALL("all"),
    PUBLIC("public"),
    PRIVATE("private");

    private String value;
    Selected(String value) {
        this.value = value;
    }
}
