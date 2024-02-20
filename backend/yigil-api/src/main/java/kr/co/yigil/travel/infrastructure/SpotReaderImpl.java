package kr.co.yigil.travel.infrastructure;

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
    public Slice<Spot> getSpotSliceInPlace(Long placeId, Pageable pageable) {
        return spotRepository.findAllByPlaceIdAndIsInCourseIsFalse(placeId, pageable);
    }
}
