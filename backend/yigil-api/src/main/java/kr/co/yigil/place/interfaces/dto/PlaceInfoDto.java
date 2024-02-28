package kr.co.yigil.place.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlaceInfoDto {
    private Long id;
    private String placeName;
    private String reviewCount;
    private String thumbnailImageUrl;
    private String rate;
    private boolean isBookmarked;
}
