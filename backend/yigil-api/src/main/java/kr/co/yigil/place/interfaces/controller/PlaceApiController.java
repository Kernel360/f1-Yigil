package kr.co.yigil.place.interfaces.controller;

import kr.co.yigil.auth.Auth;
import kr.co.yigil.auth.MemberOnly;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.place.application.PlaceFacade;
import kr.co.yigil.place.interfaces.dto.mapper.PlaceMapper;
import kr.co.yigil.place.interfaces.dto.request.PlaceStaticImageRequest;
import kr.co.yigil.place.interfaces.dto.response.PlaceStaticImageResponse;
import kr.co.yigil.place.interfaces.dto.response.PopularPlaceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/places")
public class PlaceApiController {
    private final PlaceFacade placeFacade;
    private final PlaceMapper placeMapper;
    @GetMapping("/static-image")
    @MemberOnly
    public ResponseEntity<PlaceStaticImageResponse> findPlaceStaticImage(
            PlaceStaticImageRequest request,
            @Auth Accessor accessor
    ) {
        var placeInfo = placeFacade.findPlaceStaticImage(request.getName(), request.getAddress());
        var response = placeMapper.toPlaceStaticImageResponse(placeInfo);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/popular")
    public ResponseEntity<PopularPlaceResponse> getPopularPlace(
            @Auth Accessor accessor
    ) {
        var placeInfo = placeFacade.getPopularPlace(accessor);
        var response = placeMapper.toPopularPlaceResponse(placeInfo);
        return ResponseEntity.ok().body(response);
    }

}
