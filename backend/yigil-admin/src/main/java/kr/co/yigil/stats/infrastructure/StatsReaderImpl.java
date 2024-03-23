package kr.co.yigil.stats.infrastructure;

import java.time.LocalDate;
import java.util.List;
import kr.co.yigil.region.domain.DailyRegion;
import kr.co.yigil.region.infrastructure.DailyRegionRepository;
import kr.co.yigil.stats.domain.StatsReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StatsReaderImpl implements StatsReader {
    private final DailyRegionRepository dailyRegionRepository;

    @Override
    public List<DailyRegion> getRegionStats(LocalDate startDate, LocalDate endDate) {
        return dailyRegionRepository.findBetweenDates(startDate, endDate);
    }
}
