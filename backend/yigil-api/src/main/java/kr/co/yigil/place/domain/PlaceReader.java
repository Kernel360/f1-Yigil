package kr.co.yigil.place.domain;

import java.util.Optional;
import kr.co.yigil.place.Place;

public interface PlaceReader {

    Optional<Place> findPlaceByNameAndAddress(String placeName, String placeAddress);

    Place  getPlace(Long placeId);
}
