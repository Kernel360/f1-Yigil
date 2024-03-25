package kr.co.yigil.stats.domain;

import java.time.LocalDate;
import java.util.List;
import kr.co.yigil.region.domain.DailyRegion;
import kr.co.yigil.stats.domain.StatsInfo.Recent;
import kr.co.yigil.travel.domain.Travel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final StatsReader statsReader;
    private final TravelReader travelReader;

    @Override
    public List<DailyRegion> getRegionStats(LocalDate startDate, LocalDate endDate) {
        return statsReader.getRegionStats(startDate, endDate);
    }

    @Override
    public Recent getRecentRegionStats() {
        long travelCount = travelReader.getTodayTravelCnt();
        List<Travel> recentTravel = travelReader.getRecentTravel();
        return new Recent(travelCount, recentTravel);
    }
}
