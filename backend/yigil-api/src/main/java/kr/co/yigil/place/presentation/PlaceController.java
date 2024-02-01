package kr.co.yigil.place.presentation;

import kr.co.yigil.place.application.PlaceService;
import kr.co.yigil.place.dto.response.PlaceFindResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/places")
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping("/{place_id}")
    public ResponseEntity<PlaceFindResponse> getPlace(
            @PathVariable("place_id") Long placeId
    ) {
        PlaceFindResponse placeFindResponse = placeService.getPlace(placeId);
        return ResponseEntity.ok().body(placeFindResponse);
    }
}