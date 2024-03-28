package kr.co.yigil.travel.interfaces.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MyFavoriteSpotsResponse{
    private List<FavoriteSpotDto> contents;
    private int totalPages;

    @Getter
    @Builder
    public static class FavoriteSpotDto{
        private Long spotId;
        private Long placeId;
        private String placeName;
        private double rate;
        private String imageUrl;
        private String createdDate;

        private Long writerId;
        private String writerNickname;
        private String writerProfileImageUrl;
        private String writerEmail;
        private boolean following;
    }
}
