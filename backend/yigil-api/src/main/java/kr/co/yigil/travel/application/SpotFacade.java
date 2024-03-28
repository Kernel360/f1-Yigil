package kr.co.yigil.travel.application;

import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.global.Selected;
import kr.co.yigil.travel.domain.spot.SpotCommand.ModifySpotRequest;
import kr.co.yigil.travel.domain.spot.SpotCommand.RegisterSpotRequest;
import kr.co.yigil.travel.domain.spot.SpotInfo;
import kr.co.yigil.travel.domain.spot.SpotInfo.Main;
import kr.co.yigil.travel.domain.spot.SpotInfo.MySpot;
import kr.co.yigil.travel.domain.spot.SpotInfo.Slice;
import kr.co.yigil.travel.domain.spot.SpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpotFacade {
    private final SpotService spotService;

    public Slice getSpotSliceInPlace(final Long placeId, final Accessor accessor, final Pageable pageable) {
        return spotService.getSpotSliceInPlace(placeId, accessor, pageable);
    }

    public MySpot retrieveMySpotInfoInPlace(final Long placeId, final Long memberId) {
        return spotService.retrieveMySpotInfoInPlace(placeId, memberId);
    }

    public void registerSpot(final RegisterSpotRequest command, final Long memberId) {
        spotService.registerSpot(command, memberId);
    }

    public Main retrieveSpotInfo(final Long spotId) {
        return spotService.retrieveSpotInfo(spotId);
    }

    public void modifySpot(final ModifySpotRequest command, final Long spotId, final Long memberId) {
        spotService.modifySpot(command, spotId, memberId);
    }

    public void deleteSpot(final Long spotId, final Long memberId) {
        spotService.deleteSpot(spotId, memberId);
    }

    public SpotInfo.MySpotsResponse getMemberSpotsInfo(final Long memberId,
        final Selected selected, final Pageable pageable) {
        return spotService.retrieveSpotList(memberId, selected, pageable);
    }

    public SpotInfo.MyFavoriteSpotsInfo getFavoriteSpotsInfo(Long memberId, PageRequest pageRequest) {
        return spotService.getFavoriteSpotsInfo(memberId, pageRequest);
    }
}
