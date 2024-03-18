package kr.co.yigil.travel.spot.domain;

import java.time.LocalDateTime;
import java.util.List;
import kr.co.yigil.member.SocialLoginType;
import kr.co.yigil.travel.domain.Spot;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class SpotInfoDto {

    @Data
    public static class SpotPageInfo {

        private final Page<SpotListUnit> spots;

        public SpotPageInfo(List<SpotListUnit> spots, Pageable pageable, long totalElements) {
            this.spots = new PageImpl<>(spots, pageable, totalElements);
        }
    }

    @Data
    @AllArgsConstructor
    public static class SpotListUnit {

        private final Long spotId;
        private final String placeName;
        private final String description;
        private final LocalDateTime createdAt;
        private final String ownerNickname;
        private final String ownerProfileImageUrl;
        private final int favorCount;
        private final int commentCount;

        public SpotListUnit(Spot spot, SpotAdditionalInfo additionalInfo) {
            this.spotId = spot.getId();
            this.placeName = spot.getPlace().getName();
            this.description = spot.getDescription();
            this.favorCount = additionalInfo.getFavorCount();
            this.commentCount = additionalInfo.getCommentCount();
            this.createdAt = spot.getCreatedAt();
            this.ownerNickname = spot.getMember().getNickname();
            this.ownerProfileImageUrl = spot.getMember().getProfileImageUrl();
        }
    }

    @Data
    @AllArgsConstructor
    public static class SpotDetailInfo {

        private final Long spotId;
        private final String title;
        private final String content;
        private final String placeName;
        private final String mapStaticImageUrl;
        private final String address;
        private final double x;
        private final double y;
        private final double rate;
        private final int favorCount;
        private final int commentCount;
        private final List<String> imageUrls;
        private final Long writerId;
        private final String writerName;
        private final String writerProfileImageUrl;
        private final SocialLoginType writerSocialLoginType;
        private final LocalDateTime createdAt;

        public SpotDetailInfo(Spot spot, SpotAdditionalInfo additionalInfo) {
            this.spotId = spot.getId();
            this.title = spot.getTitle();
            this.content = spot.getDescription();
            this.placeName = spot.getPlace().getName();
            this.mapStaticImageUrl = spot.getPlace().getMapStaticImageFileUrl();
            this.address = spot.getPlace().getAddress();
            this.x = spot.getPlace().getLocation().getCoordinate().x;
            this.y = spot.getPlace().getLocation().getCoordinate().y;
            this.rate = spot.getRate();
            this.imageUrls = spot.getAttachFiles().getUrls();
            this.writerId = spot.getMember().getId();
            this.writerName = spot.getMember().getNickname();
            this.writerProfileImageUrl = spot.getMember().getProfileImageUrl();
            this.writerSocialLoginType = spot.getMember().getSocialLoginType();
            this.createdAt = spot.getCreatedAt();
            this.favorCount = additionalInfo.getFavorCount();
            this.commentCount = additionalInfo.getCommentCount();
        }
    }

    @Data
    @AllArgsConstructor
    public static class SpotAdditionalInfo{
        private int favorCount;
        private int commentCount;
    }
}
