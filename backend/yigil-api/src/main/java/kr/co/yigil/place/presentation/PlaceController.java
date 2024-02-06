package kr.co.yigil.place.presentation;

import kr.co.yigil.auth.Auth;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.place.application.PlaceService;
import kr.co.yigil.place.dto.response.PlaceFindDto;
import kr.co.yigil.place.dto.response.PlaceInfoResponse;
import kr.co.yigil.place.dto.response.PlaceMapStaticImageResponse;
import kr.co.yigil.place.dto.response.RateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/places")
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping("/{place_id}")
    public ResponseEntity<PlaceInfoResponse> getPlaceInfo(
            @PathVariable("place_id") Long placeId
    ) {
        PlaceInfoResponse placeInfoResponse = placeService.getPlaceInfo(placeId);
        return ResponseEntity.ok().body(placeInfoResponse);
    }

    @GetMapping
    public ResponseEntity<Slice<PlaceFindDto>> getPlaceList(
            @PageableDefault(size = 5) Pageable pageable,
            @RequestParam(name = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = "asc", required = false) String sortOrder,
            @RequestParam(name = "keyword", required = false) String keyword // todo : querydsl 적용
    ) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        Slice<PlaceFindDto> placeListResponse = placeService.getPlaceList(pageRequest, keyword);
        return ResponseEntity.ok().body(placeListResponse);
    }

    @GetMapping("/rates")
    public ResponseEntity<RateResponse> getMemberRate(
            @RequestParam("place_id") Long placeId,
            @Auth final Accessor accessor
    ) {
        RateResponse rateResponse = placeService.getMemberRate(placeId, accessor.getMemberId());
        return ResponseEntity.ok().body(rateResponse);
    }

    @GetMapping("/static-image")
    public ResponseEntity<PlaceMapStaticImageResponse> getPlaceStaticImageFile(
            @RequestParam("name") String name,
            @RequestParam("address") String address
    ) {
        PlaceMapStaticImageResponse placeInfoResponse = placeService.getPlaceStaticImage(name, address);
        return ResponseEntity.ok().body(placeInfoResponse);
    }
}
