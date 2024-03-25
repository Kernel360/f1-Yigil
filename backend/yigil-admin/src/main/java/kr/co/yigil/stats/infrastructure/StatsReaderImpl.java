package kr.co.yigil.stats.infrastructure;

import kr.co.yigil.favor.domain.DailyFavorCount;
import kr.co.yigil.favor.domain.DailyTotalFavorCount;
import kr.co.yigil.favor.infrastructure.DailyFavorCountRepository;
import kr.co.yigil.favor.infrastructure.DailyTotalFavorCountRepository;
import kr.co.yigil.region.domain.DailyRegion;
import kr.co.yigil.region.infrastructure.DailyRegionRepository;
import kr.co.yigil.stats.domain.StatsReader;
import kr.co.yigil.travel.TravelType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StatsReaderImpl implements StatsReader {
    private final DailyRegionRepository dailyRegionRepository;
    private final DailyFavorCountRepository dailyFavorCountRepository;
    private final DailyTotalFavorCountRepository dailyTotalFavorCountRepository;

    @Override
    public List<DailyRegion> getRegionStats(LocalDate startDate, LocalDate endDate) {
        return dailyRegionRepository.findBetweenDates(startDate, endDate);
    }

    public Page<DailyTotalFavorCount> getDailyTotalFavorCounts(Pageable pageable) {
        return dailyTotalFavorCountRepository.findAll(pageable);

    }

    @Override
    public List<DailyFavorCount> getTopDailyFavorCount(LocalDate startDate, LocalDate endDate, TravelType travelType, Integer limit) {
        return dailyFavorCountRepository.findTopByTravelTypeOrderByCountDesc(startDate, endDate, travelType, limit);
    }
}
