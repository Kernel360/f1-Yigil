package kr.co.yigil.statistics.domain;

import kr.co.yigil.favor.domain.DailyFavorCount;
import kr.co.yigil.favor.domain.DailyFavorCountReader;
import kr.co.yigil.travel.TravelType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {
    private final DailyFavorCountReader dailyFavorCountReader;

    @Transactional
    @Override
    public StaticInfo.DailyFavorsInfo getDailyFavors(LocalDate startDate, LocalDate endDate, TravelType travelType, Pageable pageable) {
        Page<DailyFavorCount> dailyFavorCountPageable = dailyFavorCountReader.readDailyFavorCountBetween(startDate, endDate, travelType, pageable);
        return new StaticInfo.DailyFavorsInfo(dailyFavorCountPageable);
    }

}
