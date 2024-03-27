package kr.co.yigil.travel.domain.spot;

import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.domain.dto.SpotListDto;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class SpotInfo {

    @Getter
    @ToString
    public static class Main {
        private final Long id;
        private final String placeName;
        private final double rate;
        private final String placeAddress;
        private final String mapStaticImageFileUrl;
        private final List<String> imageUrls;
        private final LocalDateTime createDate;
        private final String description;
        private final Long ownerId;
        private final String ownerProfileImageUrl;
        private final String ownerNickname;
        private final boolean liked;
        private final boolean following;

        public Main(Spot spot) {
            this(spot, false, false);
        }

        public Main(Spot spot, boolean liked, boolean following) {
            id = spot.getId();
            placeName = spot.getPlace().getName();
            placeAddress = spot.getPlace().getAddress();
            rate = spot.getRate();
            mapStaticImageFileUrl = spot.getPlace().getMapStaticImageFileUrl();
            imageUrls = spot.getAttachFiles().getUrls();
            createDate = spot.getCreatedAt();
            description = spot.getDescription();
            ownerId = spot.getMember().getId();
            ownerProfileImageUrl = spot.getMember().getProfileImageUrl();
            ownerNickname = spot.getMember().getNickname();
            this.liked = liked;
            this.following = following;
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
        private final Long placeId;
        private final String placeName;
        private final double rate;
        private final String imageUrl;
        private final LocalDateTime createdDate;
        private final Boolean isPrivate;

        public SpotListInfo(SpotListDto spot) {
            this.spotId = spot.getSpotId();
            this.placeId = spot.getPlaceId();
            this.placeName = spot.getPlaceName();
            this.rate = spot.getRate();
            this.imageUrl = spot.getImageUrl();
            this.createdDate = spot.getCreatedDate();
            this.isPrivate = spot.getIsPrivate();
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

    @Getter
    @ToString
    public static class Slice {
        private final List<Main> spots;
        private final boolean hasNext;

        public Slice(List<Main> mains, boolean hasNext) {
            this.spots = mains;
            this.hasNext = hasNext;
        }
    }

    @Getter
    public static class MyFavoriteSpotsInfo{
        private final List<FavoriteSpotInfo> contents;
        private final int totalPages;

        public MyFavoriteSpotsInfo(List<FavoriteSpotInfo> favoriteSpots, int totalPages){
            this.contents = favoriteSpots;
            this.totalPages = totalPages;
        }
    }

    @Getter
    public static class FavoriteSpotInfo{
        private final Long spotId;
        private final Long placeId;
        private final String placeName;
        private final double rate;
        private final String imageUrl;
        private final String createdDate;

        private final Long writerId;
        private final String writerNickname;
        private final String writerProfileImageUrl;
        private final String writerEmail;
        private final boolean following;
        public FavoriteSpotInfo(Spot spot, boolean following){
            this.spotId = spot.getId();
            this.placeId = spot.getPlace().getId();
            this.placeName = spot.getPlace().getName();
            this.rate = spot.getRate();
            this.imageUrl = spot.getRepresentativeImageUrl();
            this.createdDate = spot.getCreatedAt().toString();

            this.writerId = spot.getMember().getId();
            this.writerNickname = spot.getMember().getNickname();
            this.writerProfileImageUrl = spot.getMember().getProfileImageUrl();
            this.writerEmail = spot.getMember().getEmail();
            this.following = following;
        }
    }
}
