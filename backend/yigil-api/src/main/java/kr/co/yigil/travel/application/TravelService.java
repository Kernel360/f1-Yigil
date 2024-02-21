package kr.co.yigil.travel.application;

import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.travel.domain.Travel;
import kr.co.yigil.travel.infrastructure.TravelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TravelService {

    private final TravelRepository travelRepository;

    public Travel findTravelById(Long travelId) {
        return travelRepository.findById(travelId)
                .orElseThrow(() -> new BadRequestException(ExceptionCode.NOT_FOUND_TRAVEL_ID));
    }

}
