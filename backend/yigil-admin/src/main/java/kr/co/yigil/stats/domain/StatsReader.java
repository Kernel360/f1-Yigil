package kr.co.yigil.stats.domain;

import kr.co.yigil.favor.domain.DailyFavorCount;
import kr.co.yigil.region.domain.DailyRegion;
import kr.co.yigil.travel.TravelType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface StatsReader {

    List<DailyRegion> getRegionStats(LocalDate startDate, LocalDate endDate);

    public Page<DailyFavorCount> readDailyFavorCountBetween(LocalDate startDate, LocalDate endDate, TravelType travelType, Pageable pageable);

    public List<DailyFavorCount> getTopDailyFavorCount(LocalDate startDate, LocalDate endDate, TravelType travelType, Integer limit);
}
