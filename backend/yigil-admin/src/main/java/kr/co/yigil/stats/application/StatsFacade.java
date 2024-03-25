package kr.co.yigil.stats.application;

import kr.co.yigil.region.domain.DailyRegion;
import kr.co.yigil.stats.domain.StaticInfo;
import kr.co.yigil.stats.domain.StatsService;
import kr.co.yigil.travel.TravelType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsFacade {
    private final StatsService statsService;

    public List<DailyRegion> getRegionStats(LocalDate startDate, LocalDate endDate) {
        return statsService.getRegionStats(startDate, endDate);
    }

    public StaticInfo.DailyFavorsInfo getDailyFavors(LocalDate startDate, LocalDate endDate, TravelType travelType, Pageable pageable) {
        return statsService.getDailyFavors(startDate, endDate, travelType, pageable);
    }
    public StaticInfo.DailyFavorsInfo getTopDailyFavors(LocalDate startDate, LocalDate endDate, TravelType travelType, Integer limit) {
        return statsService.getTopDailyFavors(startDate, endDate, travelType, limit);
    }
}
