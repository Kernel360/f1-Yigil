package kr.co.yigil.place.domain;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.place.domain.PlaceInfo.Keyword;
import kr.co.yigil.place.domain.PlaceInfo.Main;

public interface PlaceService {
    public List<Main> getPopularPlace(Accessor accessor);
    public List<Main> getPopularPlaceMore(Accessor accessor);
    public List<Main> getPlaceInRegion(Long regionId, Accessor accessor);
    public List<Main> getPlaceInRegionMore(Long regionId, Accessor accessor);
    public PlaceInfo.Detail retrievePlace(Long placeId, Accessor accessor);
    public PlaceInfo.MapStaticImageInfo findPlaceStaticImage(Long memberId, String placeName, String address);
    public Page<Place> getNearPlace(PlaceCommand.NearPlaceRequest command);
    public List<Keyword> getPlaceKeywords(String keyword);

    List<Main> getPopularPlaceByDemographics(Long memberId);

    List<Main> getPopularPlaceByDemographicsMore(Long memberId);

    Slice<Main> searchPlace(String keyword, Pageable pageable, Accessor accessor);

    List<Long> getMyPlaceIds(Long memberId);
}
