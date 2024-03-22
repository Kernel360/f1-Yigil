package kr.co.yigil.favor.infrastructure;

import kr.co.yigil.favor.domain.DailyFavorCount;
import kr.co.yigil.favor.domain.DailyFavorCountReader;
import kr.co.yigil.travel.TravelType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DailyFavorCountReaderImpl implements DailyFavorCountReader {
    private final DailyFavorCountRepository dailyFavorCountRepository;

    @Override
    public Page<DailyFavorCount> readDailyFavorCountBetween(LocalDate startDate, LocalDate endDate, TravelType travelType, Pageable pageable) {
        return dailyFavorCountRepository.findAllByCreatedAtBetweenAndTravelTypeOrderByCountDesc(startDate, endDate, travelType, pageable);
    }

//    public List<DailyFavorCount> getTopTravelFavor(LocalDate startDate, LocalDate endDate, TravelType travelType) {
//        return dailyFavorCountRepository.findAllByCreatedAtBetweenAndTravelTypeOrderByCountDesc(startDate, endDate, travelType);
//    }
}
