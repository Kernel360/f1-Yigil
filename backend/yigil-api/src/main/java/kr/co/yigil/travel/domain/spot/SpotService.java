package kr.co.yigil.travel.domain.spot;

import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.global.Selected;
import kr.co.yigil.travel.domain.course.CourseInfo;
import kr.co.yigil.travel.domain.spot.SpotCommand.ModifySpotRequest;
import kr.co.yigil.travel.domain.spot.SpotCommand.RegisterSpotRequest;
import kr.co.yigil.travel.domain.spot.SpotInfo.MySpotsResponse;
import kr.co.yigil.travel.domain.spot.SpotInfo.Slice;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SpotService {

    Slice getSpotSliceInPlace(Long placeId, Accessor accessor, Pageable pageable);

    public SpotInfo.MySpot retrieveMySpotInfoInPlace(Long placeId, Long memberId);

    void registerSpot(RegisterSpotRequest command, Long memberId);

    public SpotInfo.Main retrieveSpotInfo(Long spotId);

    void modifySpot(ModifySpotRequest command, Long spotId, Long memberId);

    void deleteSpot(Long spotId, Long memberId);

    MySpotsResponse retrieveSpotList(Long memberId, Selected selected, Pageable pageable);

    CourseInfo.MySpotsInfo getMySpotsDetailInfo(List<Long> spotIds, Long memberId);

    SpotInfo.MyFavoriteSpotsInfo getFavoriteSpotsInfo(Long memberId, Pageable pageRequest);
}
