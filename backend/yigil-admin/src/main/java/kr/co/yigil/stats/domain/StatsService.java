package kr.co.yigil.stats.domain;

import kr.co.yigil.region.domain.DailyRegion;
import kr.co.yigil.travel.TravelType;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface StatsService {

    List<DailyRegion> getRegionStats(LocalDate startDate, LocalDate endDate);

    StatsInfo.Recent getRecentRegionStats();

    StaticInfo.DailyTotalFavorCountInfo getDailyFavors(LocalDate startDate, LocalDate endDate);

    StaticInfo.DailyTravelsFavorCountInfo getTopDailyFavors(LocalDate startDate, LocalDate endDate);
}
