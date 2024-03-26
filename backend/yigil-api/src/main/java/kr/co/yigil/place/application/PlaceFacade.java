package kr.co.yigil.place.application;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.place.domain.Place;
import kr.co.yigil.place.domain.PlaceCommand.NearPlaceRequest;
import kr.co.yigil.place.domain.PlaceInfo;
import kr.co.yigil.place.domain.PlaceInfo.Keyword;
import kr.co.yigil.place.domain.PlaceInfo.Main;
import kr.co.yigil.place.domain.PlaceInfo.MapStaticImageInfo;
import kr.co.yigil.place.domain.PlaceService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlaceFacade {
    private final PlaceService placeService;

    public MapStaticImageInfo findPlaceStaticImage(final Long memberId, final String placeName, final String address) {
        return placeService.findPlaceStaticImage(memberId, placeName, address);
    }

    public List<Main> getPopularPlace(final Accessor accessor) {
        return placeService.getPopularPlace(accessor);
    }

    public List<Main> getPopularPlaceMore(final Accessor accessor) {
        return placeService.getPopularPlaceMore(accessor);
    }

    public PlaceInfo.Detail retrievePlaceInfo(final Long placeId, final Accessor accessor) {
        return placeService.retrievePlace(placeId, accessor);
    }

    public List<Main> getPlaceInRegion(final Long regionId, final Accessor accessor) {
        return placeService.getPlaceInRegion(regionId, accessor);
    }

    public List<Main> getPlaceInRegionMore(final Long regionId, final Accessor accessor) {
        return placeService.getPlaceInRegionMore(regionId, accessor);
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

    public Slice<Main> searchPlace(final String keyword, final Pageable pageable,  final Accessor accessor) {
        return placeService.searchPlace(keyword, pageable, accessor);
    }
    public List<Keyword> getPlaceKeywords(final String keyword) {
        return placeService.getPlaceKeywords(keyword);
    }

    public List<Long> getMyPlaceIds(final Long memberId) {
        return placeService.getMyPlaceIds(memberId);
    }

}
