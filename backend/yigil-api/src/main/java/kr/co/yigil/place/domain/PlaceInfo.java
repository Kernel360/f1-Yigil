package kr.co.yigil.place.domain;

import lombok.Getter;
import lombok.ToString;
import org.locationtech.jts.geom.Point;

import java.util.Optional;

public class PlaceInfo {

    @Getter
    @ToString
    public static class Main {
        private final Long id;
        private final String name;
        private final int reviewCount;
        private final String thumbnailImageUrl;
        private final double rate;
        private final boolean isBookmarked;

        public Main(Place place, int spotCount, double placeRate) {
            id = place.getId();
            name = place.getName();
            reviewCount = spotCount;
            thumbnailImageUrl = place.getImageFileUrl();
            rate = placeRate;
            isBookmarked = false;
        }
        public Main(Place place, int spotCount, boolean isBookmarked, double placeRate) {
            id = place.getId();
            name = place.getName();
            reviewCount = spotCount;
            thumbnailImageUrl = place.getImageFileUrl();
            rate = placeRate;
            this.isBookmarked = isBookmarked;
        }
    }

    @Getter
    @ToString
    public static class Detail {
        private final Long id;
        private final String name;
        private final String address;
        private final String thumbnailImageUrl;
        private final String mapStaticImageUrl;
        private final PointInfo point;
        private final boolean isBookmarked;
        private final double rate;
        private final int reviewCount;

        public Detail(Place place, int spotCount, double placeRate) {
            id = place.getId();
            name = place.getName();
            address = place.getAddress();
            thumbnailImageUrl = place.getImageFileUrl();
            mapStaticImageUrl = place.getMapStaticImageFileUrl();
            point = new PointInfo(place.getLocation());
            rate = placeRate;
            reviewCount = spotCount;
            isBookmarked = false;
        }
        public Detail(Place place, int spotCount, boolean isBookmarked, double placeRate) {
            id = place.getId();
            name = place.getName();
            address = place.getAddress();
            thumbnailImageUrl = place.getImageFileUrl();
            mapStaticImageUrl = place.getMapStaticImageFileUrl();
            point = new PointInfo(place.getLocation());
            rate = placeRate;
            reviewCount = spotCount;
            this.isBookmarked = isBookmarked;
        }
    }
    @Getter
    public static class PointInfo {
        private final double x;
        private final double y;

        public PointInfo(Point point) {
            this.x = point.getX();
            this.y = point.getY();
        }
    }

    @Getter
    @ToString
    public static class MapStaticImageInfo {
        private final boolean exists;
        private final String imageUrl;
        private final boolean registeredPlace;

        public MapStaticImageInfo(Optional<Place> placeOptional, boolean isRegisteredPlace) {
            if(placeOptional.isEmpty()) {
                exists = false;
                imageUrl = "";
            } else {
                exists = true;
                imageUrl = placeOptional.get()
                        .getMapStaticImageFileUrl();
            }
            this.registeredPlace = isRegisteredPlace;
        }
    }

    @Getter
    @ToString
    public static class Keyword {
        private final String keyword;

        public Keyword(String keyword) {
            this.keyword = keyword;
        }
    }
}
