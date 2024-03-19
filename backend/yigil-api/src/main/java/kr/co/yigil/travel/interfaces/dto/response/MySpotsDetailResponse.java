package kr.co.yigil.travel.interfaces.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MySpotsDetailResponse {
    private List<SpotDetailDto> spotDetails;

    @Data
    @Builder
    public static class SpotDetailDto{
        private Long spotId;
        private String placeName;
        private String placeAddress;
        private double rate;
        private String description;
        private List<String> imageUrls;
        private LocalDateTime createDate;
        private PointDto point;
    }

    @Data
    @Builder
    public static class PointDto {
        Double x;
        Double y;
    }
}
