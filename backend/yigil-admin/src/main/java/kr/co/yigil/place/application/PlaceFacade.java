package kr.co.yigil.place.application;

import kr.co.yigil.place.domain.Place;
import kr.co.yigil.place.domain.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
@RequiredArgsConstructor
public class PlaceFacade {
    private final PlaceService placeService;
    public Page<Place> getPlaces(PageRequest pageRequest) {
        return placeService.getPlaces(pageRequest);
    }

    public Place getPlaceDetail(Long placeId) {
        return placeService.getPlaceDetail(placeId);
    }

    public void updateImage(Long placeId, MultipartFile imageFile) {
        placeService.updateImage(placeId, imageFile);
    }




}
