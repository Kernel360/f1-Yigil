package kr.co.yigil.stats.domain;

import kr.co.yigil.favor.domain.DailyFavorCount;
import kr.co.yigil.favor.domain.DailyTotalFavorCount;
import kr.co.yigil.region.domain.DailyRegion;
import kr.co.yigil.travel.TravelType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final StatsReader statsReader;

    @Override
    public List<DailyRegion> getRegionStats(LocalDate startDate, LocalDate endDate) {
        return statsReader.getRegionStats(startDate, endDate);
    }

    @Transactional
    @Override
    public StaticInfo.DailyTotalFavorCountInfo getDailyFavors(Pageable pageable) {
        Page<DailyTotalFavorCount> dailyFavorCountPageable = statsReader.getDailyTotalFavorCounts(pageable);
        return new StaticInfo.DailyTotalFavorCountInfo(dailyFavorCountPageable);
    }

    @Transactional
    @Override
    public StaticInfo.DailyTravelsFavorCountInfo getTopDailyFavors(LocalDate startDate, LocalDate endDate, TravelType travelType, Integer limit) {
        List<DailyFavorCount> topDailyFavorCount = statsReader.getTopDailyFavorCount(startDate, endDate, travelType, limit);

        return new StaticInfo.DailyTravelsFavorCountInfo(topDailyFavorCount);
    }
}
