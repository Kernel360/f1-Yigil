package kr.co.yigil.stats.domain;

import kr.co.yigil.favor.domain.DailyFavorCount;
import kr.co.yigil.favor.domain.DailyTotalFavorCount;
import kr.co.yigil.region.domain.DailyRegion;
import kr.co.yigil.travel.TravelType;
import kr.co.yigil.stats.domain.StatsInfo.Recent;
import kr.co.yigil.travel.domain.Travel;
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
    private final TravelReader travelReader;

    @Transactional(readOnly = true)
    @Override
    public List<DailyRegion> getRegionStats(LocalDate startDate, LocalDate endDate) {
        return statsReader.getRegionStats(startDate, endDate);
    }

    @Transactional(readOnly = true)
    @Override
    public Recent getRecentRegionStats() {
        long travelCount = travelReader.getTodayTravelCnt();
        List<Travel> recentTravel = travelReader.getRecentTravel();
        return new Recent(travelCount, recentTravel);
    }

    @Transactional(readOnly = true)
    @Override
    public StaticInfo.DailyTotalFavorCountInfo getDailyFavors(LocalDate startDate, LocalDate endDate) {
        List<DailyTotalFavorCount> dailyFavorCountList = statsReader.getDailyTotalFavorCounts(startDate, endDate);
        return new StaticInfo.DailyTotalFavorCountInfo(dailyFavorCountList);
    }

    @Transactional(readOnly = true)
    @Override
    public StaticInfo.DailyTravelsFavorCountInfo getTopDailyFavors(LocalDate startDate, LocalDate endDate) {
        List<DailyFavorCount> topDailyFavorCount = statsReader.getTopDailyFavorCount(startDate, endDate);

        return new StaticInfo.DailyTravelsFavorCountInfo(topDailyFavorCount);
    }
}
