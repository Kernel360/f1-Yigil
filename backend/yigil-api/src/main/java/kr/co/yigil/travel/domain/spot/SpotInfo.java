package kr.co.yigil.travel.domain.spot;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
}
