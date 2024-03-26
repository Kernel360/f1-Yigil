package kr.co.yigil.stats.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class StatsDto {

    @Data
    @NoArgsConstructor
    public static class DailyTotalFavorCountResponse {
    private List<DailyTotalFavorCountDto> dailyFavors;
    }

    @Data
    @NoArgsConstructor
    public static class DailyTotalFavorCountDto {
        private Long count;
        private LocalDate date;
    }

    @Data
    @NoArgsConstructor
    public static class DailyTravelFavorsResponse {
        List<TravelFavorDto> dailyFavors;
    }

    @Data
    @NoArgsConstructor
    public static class TravelFavorDto {
        private Long travelId;
        private String travelType;
        private Long writerId;
        private String writerName;
        private String writerEmail;
        private String writerProfileImageUrl;
        private Long favorCount;
        private LocalDate date;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RegionStatsResponse {
        private List<RegionStatsInfo> regionStatsInfoList;

    }
    @Data
    @NoArgsConstructor
    public static class RegionStatsInfo {
        private String region;
        private Long referenceCount;
    }
}
