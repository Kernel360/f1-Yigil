package kr.co.yigil.travel.domain.spot;

import java.util.List;
import java.util.Optional;
import kr.co.yigil.travel.domain.Spot;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface SpotReader {
    Spot getSpot(Long spotId);

    Optional<Spot> findSpotByPlaceIdAndMemberId(Long placeId, Long memberId);

    List<Spot> getSpots(List<Long> spotIds);

    Slice<Spot> getSpotSliceInPlace(Long placeId, Pageable pageable);

    int getSpotCountInPlace(Long placeId);
}
