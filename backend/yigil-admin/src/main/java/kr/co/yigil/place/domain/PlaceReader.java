package kr.co.yigil.place.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlaceReader {

    Page<Place> getPlaces(Pageable pageRequest);

    Place getPlace(Long placeId);
}
