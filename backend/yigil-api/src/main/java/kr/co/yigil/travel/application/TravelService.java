package kr.co.yigil.travel.application;

import static kr.co.yigil.global.exception.ExceptionCode.NOT_FOUND_TRAVEL_ID;

import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.travel.Travel;
import kr.co.yigil.travel.repository.TravelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class  TravelService {
    private final TravelRepository travelRepository;

    public Travel findTravelById(Long travelId) {
        return travelRepository.findById(travelId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_TRAVEL_ID));
    }

}
