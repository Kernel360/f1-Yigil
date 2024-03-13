package kr.co.yigil.bookmark.interfaces.dto;

import kr.co.yigil.bookmark.domain.Bookmark;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkInfoDto {
    private Long bookmarkId;
    private Long placeId;
    private String placeName;
    private String placeImage;
    private Double rate;

    public BookmarkInfoDto(Bookmark bookmarkDto) {
        this.bookmarkId = bookmarkDto.getId();
        this.placeId = bookmarkDto.getPlace().getId();
        this.placeName = bookmarkDto.getPlace().getName();
        this.placeImage = bookmarkDto.getPlace().getImageFileUrl();
        this.rate = bookmarkDto.getPlace().getRate();
    }
}