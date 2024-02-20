package kr.co.yigil.travel.domain.spot;

import kr.co.yigil.travel.domain.Spot;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface SpotService {
    Slice<Spot> getSpotSliceInPlace(Long placeId, Pageable pageable);

    Long registerSpot(SpotCommand.RegisterSpotRequest request, Long memberId);
}
