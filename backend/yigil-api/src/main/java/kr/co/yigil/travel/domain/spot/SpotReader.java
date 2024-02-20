package kr.co.yigil.travel.domain.spot;

import kr.co.yigil.travel.domain.Spot;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface SpotReader {

    Slice<Spot> getSpotSliceInPlace(Long placeId, Pageable pageable);
}
