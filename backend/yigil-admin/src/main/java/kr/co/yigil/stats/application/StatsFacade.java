package kr.co.yigil.stats.application;

import java.time.LocalDate;
import java.util.List;
import kr.co.yigil.region.domain.DailyRegion;
import kr.co.yigil.stats.domain.StatsInfo;
import kr.co.yigil.stats.domain.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatsFacade {
    private final StatsService statsService;

    public List<DailyRegion> getRegionStats(LocalDate startDate, LocalDate endDate) {
        return statsService.getRegionStats(startDate, endDate);
    }

    public StatsInfo.Recent getRecentRegionStats() {
        return statsService.getRecentRegionStats();
    }
}
