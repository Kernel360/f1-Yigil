package kr.co.yigil.travel.domain.spot;

import java.util.List;
import kr.co.yigil.travel.domain.Spot;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class SpotInfo {

    @Getter
    @ToString
    public static class Main {
        private final Long spotId;
        private final List<String> imageUrlList;
        private final String ownerProfileImageUrl;
        private final String ownerNickname;
        private final String rate;
        private final String createDate;

        public Main(Spot spot) {
            this.spotId = spot.getId();
            this.imageUrlList = spot.getAttachFiles().getUrls();
            this.ownerProfileImageUrl = spot.getMember().getProfileImageUrl();
            this.ownerNickname = spot.getMember().getNickname();
            this.rate = Double.toString(spot.getRate());
            this.createDate = spot.getCreatedAt().toString();
        }
    }
}
