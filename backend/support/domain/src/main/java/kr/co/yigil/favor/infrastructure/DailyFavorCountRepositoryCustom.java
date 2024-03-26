package kr.co.yigil.favor.infrastructure;

import kr.co.yigil.favor.domain.DailyFavorCount;
import kr.co.yigil.travel.TravelType;

import java.time.LocalDate;
import java.util.List;

public interface DailyFavorCountRepositoryCustom {
    List<DailyFavorCount> findTopByTravelTypeOrderByCountDesc(LocalDate startDate, LocalDate endDate);
}
