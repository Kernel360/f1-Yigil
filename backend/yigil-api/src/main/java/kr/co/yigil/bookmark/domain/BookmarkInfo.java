package kr.co.yigil.bookmark.domain;


import lombok.Data;

@Data
public class BookmarkInfo {
    private Long bookmarkId;
    private Long placeId;
    private String placeName;
    private String placeImage;
    private Double rate;

    public BookmarkInfo(Long bookmarkId, Long placeId, String placeName, String placeImage, Double placeRate) {
        this.bookmarkId = bookmarkId;
        this.placeId = placeId;
        this.placeName = placeName;
        this.placeImage = placeImage;
        this.rate = placeRate;
    }

}
