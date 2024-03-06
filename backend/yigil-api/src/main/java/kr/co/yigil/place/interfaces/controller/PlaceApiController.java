package kr.co.yigil.place.interfaces.controller;

import kr.co.yigil.auth.Auth;
import kr.co.yigil.auth.MemberOnly;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.place.application.PlaceFacade;
import kr.co.yigil.place.interfaces.dto.PlaceDetailInfoDto;
import kr.co.yigil.place.interfaces.dto.mapper.PlaceMapper;
import kr.co.yigil.place.interfaces.dto.request.NearPlaceRequest;
import kr.co.yigil.place.interfaces.dto.request.PlaceImageRequest;
import kr.co.yigil.place.interfaces.dto.response.NearPlaceResponse;
import kr.co.yigil.place.interfaces.dto.response.PlaceStaticImageResponse;
import kr.co.yigil.place.interfaces.dto.response.PopularPlaceResponse;
import kr.co.yigil.place.interfaces.dto.response.RegionPlaceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
            PlaceImageRequest request,
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

    @GetMapping("/popular/more")
    public ResponseEntity<PopularPlaceResponse> getPopularPlaceMore(@Auth Accessor accessor) {
        var placeInfo = placeFacade.getPopularPlaceMore(accessor);
        var response = placeMapper.toPopularPlaceResponse(placeInfo);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{placeId}")
    public ResponseEntity<PlaceDetailInfoDto> retrievePlace(
            @PathVariable("placeId") Long placeId,
            @Auth Accessor accessor
    ) {
        var placeInfo = placeFacade.retrievePlaceInfo(placeId, accessor);
        var response = placeMapper.toPlaceDetailInfoDto(placeInfo);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/region/{regionId}")
    public ResponseEntity<RegionPlaceResponse> getRegionPlace(
            @PathVariable("regionId") Long regionId,
            @Auth Accessor accessor
    ) {
        var placeInfo = placeFacade.getPlaceInRegion(regionId, accessor);
        var response = placeMapper.toRegionPlaceResponse(placeInfo);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/region/{regionId}/more")
    public ResponseEntity<RegionPlaceResponse> getRegionPlaceMore(
            @PathVariable("regionId") Long regionId,
            @Auth Accessor accessor
    ) {
        var placeInfo = placeFacade.getPlaceInRegionMore(regionId, accessor);
        var response = placeMapper.toRegionPlaceResponse(placeInfo);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/near")
    public ResponseEntity<NearPlaceResponse> getNearPlace(NearPlaceRequest request) {
        var nearPlaceCommand = placeMapper.toNearPlaceCommand(request);
        var placeInfo = placeFacade.getNearPlace(nearPlaceCommand);
        var response = placeMapper.toNearPlaceResponse(placeInfo);
        return ResponseEntity.ok().body(response);
    }
}
