package kr.co.yigil.travel.spot.domain;

import java.time.LocalDateTime;
import java.util.List;
import kr.co.yigil.travel.domain.Spot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class AdminSpotInfoDto {

    @Data
    @NoArgsConstructor
    public static class AdminSpotList {

        private Page<SpotList> spots;

        public AdminSpotList(List<SpotList> spots, Pageable pageable, long totalElements) {
            this.spots = new PageImpl<>(spots, pageable, totalElements);
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SpotList {

        private Long spotId;
        private String title;
        private LocalDateTime createdAt;
        private int favorCount;
        private int commentCount;

        public SpotList(Spot spot, SpotAdditionalInfo additionalInfo) {
            this.spotId = spot.getId();
            this.title = spot.getTitle();
            this.favorCount = additionalInfo.getFavorCount();
            this.commentCount = additionalInfo.getCommentCount();
            this.createdAt = spot.getCreatedAt();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AdminSpotDetailInfo {

        private Long spotId;
        private String title;
        private String content;
        private String placeName;

        private String mapStaticImageUrl;
        private double rate;
        private int favorCount;
        private int commentCount;
        private List<String> imageUrls;

        private Long writerId;
        private String writerName;
        private LocalDateTime createdAt;

        public AdminSpotDetailInfo(Spot spot, SpotAdditionalInfo additionalInfo) {
            this.spotId = spot.getId();
            this.title = spot.getTitle();
            this.content = spot.getDescription();
            this.placeName = spot.getPlace().getName();
            this.mapStaticImageUrl = spot.getPlace().getMapStaticImageFileUrl();
            this.rate = spot.getRate();
            this.favorCount = additionalInfo.getFavorCount();
            this.commentCount = additionalInfo.getCommentCount();
            this.imageUrls = spot.getAttachFiles().getUrls();
            this.writerId = spot.getMember().getId();
            this.writerName = spot.getMember().getNickname();
            this.createdAt = spot.getCreatedAt();
        }
    }

    @Data
    @AllArgsConstructor
    public static class SpotAdditionalInfo{
        private int favorCount;
        private int commentCount;
    }
}
