package kr.co.yigil.travel.domain.spot;

import kr.co.yigil.global.Selected;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.domain.dto.SpotListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Optional;

public interface SpotReader {
    Spot getSpot(Long spotId);

    Optional<Spot> findSpotByPlaceIdAndMemberId(Long placeId, Long memberId);

    List<Spot> getSpots(List<Long> spotIds);

    Slice<Spot> getSpotSliceInPlace(Long placeId, Pageable pageable);

    int getSpotCountInPlace(Long placeId);

    Page<Spot> getSpotSliceByMemberId(Long memberId, Pageable pageable);

    Page<SpotListDto> getMemberSpotList(Long memberId, Selected selected, Pageable pageable);

    boolean isExistPlace(Long placeId, Long memberId);

    double getSpotTotalRateInPlace(Long placeId);

    boolean isExistSpot(Long spotId, Long memberId);

    List<Spot> getMemberSpots(Long memberId, List<Long> spotIds);

    List<Long> getMySpotPlaceIds(Long memberId);
}
