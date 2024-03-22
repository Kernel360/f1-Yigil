package kr.co.yigil.place.domain;

import lombok.Getter;
import lombok.ToString;

public class PlaceInfo {

    @Getter
    @ToString
    public static class Map {
        private final Long id;
        private final String name;
        private final double x;
        private final double y;

        public Map(Place place) {
            id = place.getId();
            name = place.getName();
            x = place.getLocation().getX();
            y = place.getLocation().getY();
        }
    }


}
