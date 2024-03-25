package kr.co.yigil.stats.domain;

import kr.co.yigil.favor.domain.DailyFavorCount;
import kr.co.yigil.favor.domain.DailyTotalFavorCount;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public class StaticInfo {

    @Data
    public static class DailyTravelsFavorCountInfo {
        List<DailyTravelFavorDetail> dailyFavors;
        int totalPages;

        public DailyTravelsFavorCountInfo(List<DailyFavorCount> dailyFavorCountList){
            this.dailyFavors = dailyFavorCountList.stream().map(DailyTravelFavorDetail::new).toList();
            this.totalPages = 1;
        }
    }

    @Data
    public static class DailyTravelFavorDetail {
        Long travelId;
        String travelType;
        String title;
        Long writerId;
        String writerName;
        String writerEmail;
        String writerProfileImageUrl;
        Long favorCount;
        LocalDate date;

        public DailyTravelFavorDetail(DailyFavorCount dailyFavorCount) {
            this.travelId = dailyFavorCount.getTravel().getId();
            this.travelType = dailyFavorCount.getTravelType().name();
            this.title = dailyFavorCount.getTravel().getTitle();
            this.writerId = dailyFavorCount.getWriter().getId();
            this.writerName = dailyFavorCount.getWriter().getNickname();
            this.writerEmail = dailyFavorCount.getWriter().getEmail();
            this.writerProfileImageUrl = dailyFavorCount.getWriter().getProfileImageUrl();
            this.favorCount = dailyFavorCount.getCount();
            this.date = dailyFavorCount.getCreatedAt();
        }
    }


    @Data
    public static class DailyTotalFavorCountInfo {
        private List<FavorTotalCountInfo> dailyFavors;
        private int totalPages;

        public DailyTotalFavorCountInfo(Page<DailyTotalFavorCount> dailyFavors){
            this.dailyFavors = dailyFavors.getContent().stream().map(FavorTotalCountInfo::new).toList();
            this.totalPages = dailyFavors.getTotalPages();
        }
    }

    @Data
    public static class FavorTotalCountInfo {
        private LocalDate date;
        private Long count;

        public FavorTotalCountInfo(DailyTotalFavorCount dailyFavorCount){
            this.date = dailyFavorCount.getDate();
            this.count = dailyFavorCount.getCount();
        }
    }
}
