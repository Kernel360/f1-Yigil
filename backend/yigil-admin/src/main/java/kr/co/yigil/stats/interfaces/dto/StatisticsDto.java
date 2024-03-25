package kr.co.yigil.stats.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class StatisticsDto {

    @Data
    @NoArgsConstructor
    public static class DailyFavorsResponse{
        List<DailyFavorDto> dailyFavors;
        int totalPages;
    }

    @Data
    @NoArgsConstructor
    public static class DailyFavorDto{
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
