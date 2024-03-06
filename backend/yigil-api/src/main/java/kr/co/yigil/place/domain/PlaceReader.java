package kr.co.yigil.place.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface PlaceReader {

    Optional<Place> findPlaceByNameAndAddress(String placeName, String placeAddress);

    Place getPlace(Long placeId);

    List<Place> getPopularPlace();

    List<Place> getPlaceInRegion(Long regionId);

    List<Place> getPlaceInRegionMore(Long regionId);

    Page<Place> getNearPlace(PlaceCommand.NearPlaceRequest command);
}
