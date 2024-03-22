package kr.co.yigil.statistics.domain;

import kr.co.yigil.travel.TravelType;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface StatisticsService {


    StaticInfo.DailyFavorsInfo getDailyFavors(LocalDate startDate, LocalDate endDate, TravelType travelType, Pageable pageable);
}
