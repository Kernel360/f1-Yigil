package kr.co.yigil.place.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class PlaceCommand {

    @Getter
    @Builder
    @ToString
    public static class NearPlaceRequest {
        private final Coordinate minCoordinate;
        private final Coordinate maxCoordinate;
        private final int pageNo;
    }

    @Getter
    @Builder
    @ToString
    public static class Coordinate {
        private final double x;
        private final double y;
    }
}
