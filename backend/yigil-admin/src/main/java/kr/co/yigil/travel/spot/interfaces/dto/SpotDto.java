package kr.co.yigil.travel.spot.interfaces.dto;


import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

public class SpotDto {


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SpotsResponse {
        private Page<SpotListInfo> spots;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor

    public static class SpotListInfo {

        private Long spotId;
        private String title;
        private LocalDateTime createdAt;
        private int favorCount;
        private int commentCount;
    }



    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SpotDetailResponse {
        private Long spotId;
        private String title;
        private String content;
        private String placeName;

        private String mapStaticImageUrl;
        private LocalDateTime createdAt;
        private double rate;
        private int favorCount;
        private int commentCount;
        private List<String> imageUrls;

        private Long writerId;
        private String writerName;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SpotDeleteResponse {
        private String message;
    }

}
