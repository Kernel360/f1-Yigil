package kr.co.yigil.stats.domain;

import kr.co.yigil.favor.domain.DailyFavorCount;
import kr.co.yigil.favor.domain.DailyTotalFavorCount;
import kr.co.yigil.region.domain.DailyRegion;
import kr.co.yigil.travel.TravelType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface StatsReader {

    List<DailyRegion> getRegionStats(LocalDate startDate, LocalDate endDate);

    public List<DailyTotalFavorCount> getDailyTotalFavorCounts(LocalDate startDate, LocalDate endDate);

    public List<DailyFavorCount> getTopDailyFavorCount(LocalDate startDate, LocalDate endDate);
}
