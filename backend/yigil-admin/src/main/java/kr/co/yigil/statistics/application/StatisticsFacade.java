package kr.co.yigil.statistics.application;

import kr.co.yigil.statistics.domain.StaticInfo;
import kr.co.yigil.statistics.domain.StatisticsService;
import kr.co.yigil.travel.TravelType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class StatisticsFacade {
    private final StatisticsService statisticsService;

    public StaticInfo.DailyFavorsInfo getDailyFavors(LocalDate startDate, LocalDate endDate, TravelType travelType, Pageable pageable) {
        return statisticsService.getDailyFavors(startDate, endDate, travelType, pageable);
    }
    public StaticInfo.DailyFavorsInfo getTopDailyFavors(LocalDate startDate, LocalDate endDate, TravelType travelType, Integer limit) {
        return statisticsService.getTopDailyFavors(startDate, endDate, travelType, limit);
    }

//    public StaticInfo.DailyFavorsInfo getList7DaysInfo(Pageable pageable){
//        LocalDate endDate = LocalDate.now();
//        LocalDate startDate = endDate.minusDays(6);
//        return statisticsService.getDailyFavors(startDate, endDate, pageable);
//    }
}
