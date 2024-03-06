package kr.co.yigil.place.application;

import java.util.List;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.place.domain.Place;
import kr.co.yigil.place.domain.PlaceCommand.NearPlaceRequest;
import kr.co.yigil.place.domain.PlaceInfo;
import kr.co.yigil.place.domain.PlaceInfo.Main;
import kr.co.yigil.place.domain.PlaceInfo.MapStaticImageInfo;
import kr.co.yigil.place.domain.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaceFacade {
    private final PlaceService placeService;

    public MapStaticImageInfo findPlaceStaticImage(final String placeName, final String address) {
        return placeService.findPlaceStaticImage(placeName, address);
    }

    public List<Main> getPopularPlace(final Accessor accessor) {
        return placeService.getPopularPlace(accessor);
    }

    public PlaceInfo.Detail retrievePlaceInfo(final Long placeId, final Accessor accessor) {
        return placeService.retrievePlace(placeId, accessor);
    }

    public List<Main> getPlaceInRegion(final Long regionId, final Accessor accessor) {
        return placeService.getPlaceInRegion(regionId, accessor);
    }

    public Page<Place> getNearPlace(final NearPlaceRequest command) {
        return placeService.getNearPlace(command);
    }

    public List<Main> getPopularPlaceByDemographics(final Long memberId) {
        return placeService.getPopularPlaceByDemographics(memberId);
    }

    public List<Main> getPopularPlaceByDemographicsMore(final Long memberId) {
        return placeService.getPopularPlaceByDemographicsMore(memberId);
    }
}
