package kr.co.yigil.travel.infrastructure;

import static kr.co.yigil.global.exception.ExceptionCode.NOT_FOUND_SPOT_ID;

import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.domain.spot.SpotReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpotReaderImpl implements SpotReader {

    private final SpotRepository spotRepository;

    @Override
    public Spot getSpot(Long spotId) {
        return spotRepository.findById(spotId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_SPOT_ID));
    }

    @Override
    public Slice<Spot> getSpotSliceInPlace(Long placeId, Pageable pageable) {
        return spotRepository.findAllByPlaceIdAndIsInCourseIsFalseAndIsPrivateIsFalse(placeId, pageable);
    }

    @Override
    public int getSpotCountInPlace(Long placeId) {
        return spotRepository.countByPlaceId(placeId);
    }
}
