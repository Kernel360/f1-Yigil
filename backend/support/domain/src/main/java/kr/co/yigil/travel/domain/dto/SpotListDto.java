package kr.co.yigil.travel.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class SpotListDto extends ImageFileDto {

    private final Long spotId;
    private final Long placeId;
    private final String placeName;
    private final double rate;
    private final String imageUrl;
    private final LocalDateTime createdDate;
    private final Boolean isPrivate;

    @QueryProjection
    public SpotListDto(Long spotId, Long placeId, String placeName, double rate, String imageUrl,
        LocalDateTime createdDate, Boolean isPrivate) {
        this.spotId = spotId;
        this.placeId = placeId;
        this.placeName = placeName;
        this.rate = rate;
        this.imageUrl = getImageUrl(imageUrl);
        this.createdDate = createdDate;
        this.isPrivate = isPrivate;
    }
}
