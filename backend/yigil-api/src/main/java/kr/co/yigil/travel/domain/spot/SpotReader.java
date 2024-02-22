package kr.co.yigil.travel.domain.spot;

import java.util.List;
import kr.co.yigil.travel.domain.Spot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface SpotReader {
    Spot getSpot(Long spotId);

    List<Spot> getSpots(List<Long> spotIds);

    Slice<Spot> getSpotSliceInPlace(Long placeId, Pageable pageable);

    int getSpotCountInPlace(Long placeId);

    Page<Spot> getSpotSliceByMemberId(Long memberId, Pageable pageable);
}
