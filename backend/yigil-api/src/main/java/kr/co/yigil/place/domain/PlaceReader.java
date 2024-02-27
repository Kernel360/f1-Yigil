package kr.co.yigil.place.domain;

import java.util.List;
import java.util.Optional;

public interface PlaceReader {

    Optional<Place> findPlaceByNameAndAddress(String placeName, String placeAddress);

    Place getPlace(Long placeId);

    List<Place> getPopularPlace();
}
