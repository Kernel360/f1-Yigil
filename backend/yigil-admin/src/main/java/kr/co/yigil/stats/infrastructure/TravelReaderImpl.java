package kr.co.yigil.stats.infrastructure;

import java.util.List;
import kr.co.yigil.stats.domain.TravelReader;
import kr.co.yigil.travel.domain.Travel;
import kr.co.yigil.travel.infrastructure.TravelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TravelReaderImpl implements TravelReader {

    private final TravelRepository travelRepository;

    @Override
    public long getTodayTravelCnt() {
        return travelRepository.countByCreatedAtToday();
    }

    public List<Travel> getRecentTravel() {
        return travelRepository.findTop5ByOrderByCreatedAtDesc();
    }
}
