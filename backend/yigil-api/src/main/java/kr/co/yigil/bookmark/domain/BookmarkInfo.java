package kr.co.yigil.bookmark.domain;


import lombok.Data;

@Data
public class BookmarkInfo {
    private Long bookmarkId;
    private Long placeId;
    private String placeName;
    private String placeImage;
    private Double rate;

    public BookmarkInfo(Bookmark bookmark) {
        this.bookmarkId = bookmark.getId();
        this.placeId = bookmark.getPlace().getId();
        this.placeName = bookmark.getPlace().getName();
        this.placeImage = bookmark.getPlace().getImageFileUrl();
        this.rate = bookmark.getPlace().getRate();
    }

}
