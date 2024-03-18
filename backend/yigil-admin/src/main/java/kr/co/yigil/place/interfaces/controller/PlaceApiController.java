package kr.co.yigil.place.interfaces.controller;


import kr.co.yigil.global.SortBy;
import kr.co.yigil.global.SortOrder;
import kr.co.yigil.place.application.PlaceFacade;
import kr.co.yigil.place.domain.Place;
import kr.co.yigil.place.interfaces.dto.mapper.PlaceMapper;
import kr.co.yigil.place.interfaces.dto.request.PlaceImageUpdateRequest;
import kr.co.yigil.place.interfaces.dto.response.PlaceDetailResponse;
import kr.co.yigil.place.interfaces.dto.response.PlaceImageUpdateResponse;
import kr.co.yigil.place.interfaces.dto.response.PlacesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/places")
public class PlaceApiController {

    private final PlaceFacade placeFacade;
    private final PlaceMapper placeMapper;

    @GetMapping
    public ResponseEntity<PlacesResponse> getPlaces(
            @PageableDefault(size = 5, page = 1) Pageable pageable,
            @RequestParam(name = "sortBy", defaultValue = "created_at", required = false) SortBy sortBy,
            @RequestParam(name = "sortOrder", defaultValue = "desc", required = false) SortOrder sortOrder
    ) {
        Sort.Direction direction = Sort.Direction.fromString(sortOrder.getValue().toUpperCase());
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber() - 1,
                pageable.getPageSize(),
                Sort.by(direction, sortBy.getValue()));
        var places = placeFacade.getPlaces(pageRequest);
        var response = placeMapper.toResponse(places);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<PlaceDetailResponse> getPlaceDetail(@RequestParam Long placeId) {
        Place place = placeFacade.getPlaceDetail(placeId);
        PlaceDetailResponse response = placeMapper.toResponse(place);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update-image")
    public ResponseEntity<PlaceImageUpdateResponse> updateImage(@ModelAttribute PlaceImageUpdateRequest request) {
        placeFacade.updateImage(request.getPlaceId(), request.getImageFile());
        return ResponseEntity.ok(new PlaceImageUpdateResponse("이미지 업데이트 완료"));
    }



}
