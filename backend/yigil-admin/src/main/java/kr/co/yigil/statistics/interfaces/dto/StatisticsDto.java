package kr.co.yigil.statistics.interfaces.dto;

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
}
