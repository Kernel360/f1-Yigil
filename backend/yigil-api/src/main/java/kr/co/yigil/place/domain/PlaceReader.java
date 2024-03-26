package kr.co.yigil.place.domain;

import java.util.List;
import java.util.Optional;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.member.Ages;
import kr.co.yigil.member.Gender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface PlaceReader {

    Optional<Place> findPlaceByNameAndAddress(String placeName, String placeAddress);

    Place getPlace(Long placeId);

    List<Place> getPopularPlace();

    List<Place> getPlaceInRegion(Long regionId);

    List<Place> getPlaceInRegionMore(Long regionId);

    Page<Place> getNearPlace(PlaceCommand.NearPlaceRequest command);

    List<String> getPlaceKeywords(String keyword);

    List<Place> getPopularPlaceByDemographics(Ages ages, Gender gender);

    List<Place> getPopularPlaceByDemographicsMore(Ages ages, Gender gender);

    Slice<Place> getPlacesByKeyword(String keyword, Pageable pageable);

}
