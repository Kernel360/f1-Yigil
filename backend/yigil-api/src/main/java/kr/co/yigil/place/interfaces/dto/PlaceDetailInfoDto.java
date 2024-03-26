package kr.co.yigil.place.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlaceDetailInfoDto {
    private Long id;
    private String placeName;
    private String address;
    private String thumbnailImageUrl;
    private String mapStaticImageUrl;
    private PointDto point;
    private boolean isBookmarked;
    private double rate;
    private int reviewCount;

    @Data
    public static class PointDto {
        private double x;
        private double y;
    }
}
