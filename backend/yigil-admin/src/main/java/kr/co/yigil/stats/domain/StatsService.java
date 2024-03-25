package kr.co.yigil.stats.domain;

import java.time.LocalDate;
import java.util.List;
import kr.co.yigil.region.domain.DailyRegion;

public interface StatsService {

    List<DailyRegion> getRegionStats(LocalDate startDate, LocalDate endDate);

    StatsInfo.Recent getRecentRegionStats();
}
