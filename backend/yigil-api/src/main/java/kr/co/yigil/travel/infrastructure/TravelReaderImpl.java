package kr.co.yigil.travel.infrastructure;

import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.travel.domain.Travel;
import kr.co.yigil.travel.domain.TravelReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TravelReaderImpl implements TravelReader {
    private final TravelRepository travelRepository;

    @Override
    public Travel getTravel(Long travelId) {
        return travelRepository.findById(travelId)
                .orElseThrow(() -> new BadRequestException(ExceptionCode.NOT_FOUND_TRAVEL_ID));
    }
}
