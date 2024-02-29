package kr.co.yigil.place.domain;

import java.util.Optional;
import lombok.Getter;
import lombok.ToString;

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

        public Main(Place place, int spotCount) {
            id = place.getId();
            name = place.getName();
            reviewCount = spotCount;
            thumbnailImageUrl = place.getImageFileUrl();
            rate = place.getRate();
            isBookmarked = false;
        }
        public Main(Place place, int spotCount, boolean isBookmarked) {
            id = place.getId();
            name = place.getName();
            reviewCount = spotCount;
            thumbnailImageUrl = place.getImageFileUrl();
            rate = place.getRate();
            this.isBookmarked = isBookmarked;
        }
    }

    @Getter
    @ToString
    public static class MapStaticImageInfo {
        private final String imageUrl;
        private final boolean exists;

        public MapStaticImageInfo(Optional<Place> placeOptional) {
            if(placeOptional.isEmpty()) {
                exists = false;
                imageUrl = "";
            } else {
                exists = true;
                imageUrl = placeOptional.get()
                        .getMapStaticImageFileUrl();
            }
        }
    }
}
