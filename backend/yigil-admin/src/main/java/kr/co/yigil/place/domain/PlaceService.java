package kr.co.yigil.place.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface PlaceService {

    Page<Place> getPlaces(Pageable pageRequest);

    Place getPlaceDetail(Long placeId);

    void updateImage(Long placeId, MultipartFile imageFile);

}
