package kr.co.yigil.favor.domain;

import kr.co.yigil.travel.TravelType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface DailyFavorCountReader {

    public Page<DailyFavorCount> readDailyFavorCountBetween(LocalDate startDate, LocalDate endDate, TravelType travelType, Pageable pageable);

    public List<DailyFavorCount> getTopDailyFavorCount(LocalDate startDate, LocalDate endDate, TravelType travelType, Integer limit);
}
