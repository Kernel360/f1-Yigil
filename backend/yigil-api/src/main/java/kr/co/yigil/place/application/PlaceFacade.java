package kr.co.yigil.place.application;

import java.util.List;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.place.domain.PlaceInfo;
import kr.co.yigil.place.domain.PlaceInfo.Main;
import kr.co.yigil.place.domain.PlaceInfo.MapStaticImageInfo;
import kr.co.yigil.place.domain.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaceFacade {
    private final PlaceService placeService;

    public MapStaticImageInfo findPlaceStaticImage(String placeName, String address) {
        return placeService.findPlaceStaticImage(placeName, address);
    }

    public List<Main> getPopularPlace(Accessor accessor) {
        return placeService.getPopularPlace(accessor);
    }

    public PlaceInfo.Detail retrievePlaceInfo(Long placeId, Accessor accessor) {
        return placeService.retrievePlace(placeId, accessor);
    }

    public List<Main> getPlaceInRegion(Long regionId, Accessor accessor) {
        return placeService.getPlaceInRegion(regionId, accessor);
    }
}
