package kr.co.yigil.place.interfaces.controller;


import kr.co.yigil.global.SortBy;
import kr.co.yigil.global.SortOrder;
import kr.co.yigil.place.application.PlaceFacade;
import kr.co.yigil.place.domain.Place;
import kr.co.yigil.place.domain.PlaceCommand;
import kr.co.yigil.place.domain.PlaceCommand.PlaceMapCommand;
import kr.co.yigil.place.interfaces.dto.mapper.PlaceMapper;
import kr.co.yigil.place.interfaces.dto.request.PlaceImageUpdateRequest;
import kr.co.yigil.place.interfaces.dto.response.PlaceDetailResponse;
import kr.co.yigil.place.interfaces.dto.response.PlaceImageUpdateResponse;
import kr.co.yigil.place.interfaces.dto.response.PlaceMapResponse;
import kr.co.yigil.place.interfaces.dto.response.PlacesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/places")
public class PlaceApiController {

    private final PlaceFacade placeFacade;
    private final PlaceMapper placeMapper;

    @GetMapping
    public ResponseEntity<PlaceMapResponse> getPlaces(
            @RequestParam(name = "startX") double startX,
            @RequestParam(name = "startY") double startY,
            @RequestParam(name = "endX") double endX,
            @RequestParam(name = "endY") double endY
    ) {
        var places = placeFacade.getPlaces(new PlaceMapCommand(startX, startY, endX, endY));
        var response = placeMapper.toResponse(places);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{placeId}")
    public ResponseEntity<PlaceDetailResponse> getPlaceDetail(@PathVariable Long placeId) {
        Place place = placeFacade.getPlaceDetail(placeId);
        PlaceDetailResponse response = placeMapper.toResponse(place);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update-image")
    public ResponseEntity<PlaceImageUpdateResponse> updateImage(@ModelAttribute PlaceImageUpdateRequest request) {
        PlaceCommand.UpdateImageCommand command = placeMapper.toCommand(request);
        placeFacade.updateImage(command);
        return ResponseEntity.ok(new PlaceImageUpdateResponse("이미지 업데이트 완료"));
    }
}
