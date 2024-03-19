package kr.co.yigil.place.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlaceService {

    Page<Place> getPlaces(Pageable pageRequest);

    Place getPlaceDetail(Long placeId);

    void updateImage(PlaceCommand.UpdateImageCommand command);

}
