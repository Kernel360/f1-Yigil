package kr.co.yigil.travel;

import lombok.Getter;

@Getter
public enum TravelType {
    ALL("all"),
    SPOT("spot"),
    COURSE("course");

    private final String name;

    TravelType(String name) {
        this.name = name;
    }
}
