package kr.co.yigil.travel.util;

import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.travel.domain.Travel;
import kr.co.yigil.travel.domain.repository.SpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TravelUtils {
    private SpotRepository spotRepository;

    public Travel findSpotById(Long spotId){
        return spotRepository.findById(spotId).orElseThrow(
            () -> new BadRequestException(ExceptionCode.NOT_FOUND_SPOT_ID)
        );
    }

}
