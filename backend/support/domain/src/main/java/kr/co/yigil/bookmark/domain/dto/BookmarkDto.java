package kr.co.yigil.bookmark.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class BookmarkDto {

    private final Long bookmarkId;
    private final Long placeId;
    private final String placeName;
    private final String placeImage;
    private Double rate;

    @QueryProjection
    public BookmarkDto(Long bookmarkId, Long placeId, String placeName, String placeImage, Double rate) {
        this.bookmarkId = bookmarkId;
        this.placeId = placeId;
        this.placeName = placeName;
        this.placeImage = placeImage;
        this.rate = rate;
    }
}
