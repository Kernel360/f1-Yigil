package kr.co.yigil.bookmark.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkInfoDto {
    private Long placeId;
    private String placeName;
    private String placeImage;
    private Double rate;
    private Boolean isBookmarked;

}
