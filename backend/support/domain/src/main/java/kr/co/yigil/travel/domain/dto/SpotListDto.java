package kr.co.yigil.travel.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class SpotListDto {

    private final Long spotId;
    private final Long placeId;
    private final String title;
    private final double rate;
    private final String imageUrl;
    private final LocalDateTime createdDate;
    private final Boolean isPrivate;

    @QueryProjection
    public SpotListDto(Long spotId, Long placeId, String title, double rate, String imageUrl,
        LocalDateTime createdDate, Boolean isPrivate) {
        this.spotId = spotId;
        this.placeId = placeId;
        this.title = title;
        this.rate = rate;
        this.imageUrl = imageUrl;
        this.createdDate = createdDate;
        this.isPrivate = isPrivate;
    }

}
