package kr.co.yigil.travel.domain.spot;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import kr.co.yigil.travel.domain.Spot;
import lombok.Getter;
import lombok.ToString;

public class SpotInfo {

    @Getter
    @ToString
    public static class Main {
        private final String placeName;
        private final double rate;
        private final String placeAddress;
        private final String mapStaticImageFileUrl;
        private final List<String> imageUrls;
        private final LocalDateTime createDate;
        private final String description;

        public Main(Spot spot) {
            placeName = spot.getPlace().getName();
            placeAddress = spot.getPlace().getAddress();
            rate = spot.getRate();
            mapStaticImageFileUrl = spot.getPlace().getMapStaticImageFile().getFileUrl();
            imageUrls = spot.getAttachFiles().getUrls();
            createDate = spot.getCreatedAt();
            description = spot.getDescription();
        }
    }

    @Getter
    @ToString
    public static class MySpotsResponse {

        private final List<SpotListInfo> content;
        private final int totalPages;

        public MySpotsResponse(List<SpotListInfo> spotList, int totalPages) {
            this.content = spotList;
            this.totalPages = totalPages;
        }
    }

    @Getter
    @ToString
    public static class SpotListInfo {

        private final Long spotId;
        private final String title;
        private final double rate;
        private final String imageUrl;
        private final LocalDateTime createdDate;
        private final Boolean isPrivate;

        public SpotListInfo(Spot spot) {
            this.spotId = spot.getId();
            this.title = spot.getTitle();
            this.rate = spot.getRate();
            this.imageUrl = spot.getAttachFiles().getUrls().getFirst();
            this.createdDate = spot.getCreatedAt();
            this.isPrivate = spot.isPrivate();
        }
    }



    @Getter
    @ToString
    public static class MySpot {
        private final double rate;
        private final List<String> imageUrls;
        private final LocalDateTime createDate;
        private final String description;
        private final boolean exists;

        public MySpot(Optional<Spot> spotOptional) {
            if(spotOptional.isEmpty()) {
                exists = false;
                rate = 0;
                imageUrls = List.of("");
                createDate = LocalDateTime.now();
                description = "";
            } else {
                exists = true;
                var spot = spotOptional.get();
                rate = spot.getRate();
                imageUrls = spot.getAttachFiles().getUrls();
                createDate = spot.getCreatedAt();
                description = spot.getDescription();
            }
        }
    }
}
