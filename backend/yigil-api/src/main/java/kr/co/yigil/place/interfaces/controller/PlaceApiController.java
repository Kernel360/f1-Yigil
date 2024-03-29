package kr.co.yigil.place.interfaces.controller;

import kr.co.yigil.place.interfaces.dto.response.MyPlaceIdResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.yigil.auth.Auth;
import kr.co.yigil.auth.MemberOnly;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.global.SortBy;
import kr.co.yigil.global.SortOrder;
import kr.co.yigil.place.application.PlaceFacade;
import kr.co.yigil.place.interfaces.dto.PlaceDetailInfoDto;
import kr.co.yigil.place.interfaces.dto.mapper.PlaceMapper;
import kr.co.yigil.place.interfaces.dto.request.NearPlaceRequest;
import kr.co.yigil.place.interfaces.dto.request.PlaceImageRequest;
import kr.co.yigil.place.interfaces.dto.response.NearPlaceResponse;
import kr.co.yigil.place.interfaces.dto.response.PlaceKeywordResponse;
import kr.co.yigil.place.interfaces.dto.response.PlaceSearchResponse;
import kr.co.yigil.place.interfaces.dto.response.PlaceStaticImageResponse;
import kr.co.yigil.place.interfaces.dto.response.PopularPlaceResponse;
import kr.co.yigil.place.interfaces.dto.response.RegionPlaceResponse;
import lombok.RequiredArgsConstructor;

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

        var placeInfo = placeFacade.findPlaceStaticImage(accessor.getMemberId(), request.getName(), request.getAddress());
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
    public ResponseEntity<PopularPlaceResponse> getPopularPlaceMore(
            @Auth Accessor accessor
    ) {
        var placeInfo = placeFacade.getPopularPlaceMore(accessor);
        var response = placeMapper.toPopularPlaceResponse(placeInfo);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/popular-demographics")
    @MemberOnly
    public ResponseEntity<PopularPlaceResponse> getPopularPlaceByDemographics(
            @Auth Accessor accessor
    ) {
        var placeInfo = placeFacade.getPopularPlaceByDemographics(accessor.getMemberId());
        var response = placeMapper.toPopularPlaceResponse(placeInfo);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/popular-demographics-more")
    @MemberOnly
    public ResponseEntity<PopularPlaceResponse> getPopularPlaceByDemographicsMore(
            @Auth Accessor accessor
    ) {
        var placeInfo = placeFacade.getPopularPlaceByDemographicsMore(accessor.getMemberId());
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
    public ResponseEntity<NearPlaceResponse> getNearPlace(NearPlaceRequest request, @Auth Accessor accessor) {
        var nearPlaceCommand = placeMapper.toNearPlaceCommand(request);
        var placeInfo = placeFacade.getNearPlace(nearPlaceCommand);
        var response = placeMapper.toNearPlaceResponse(placeInfo);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/search")
    public ResponseEntity<PlaceSearchResponse> searchPlace(
            @RequestParam(name = "keyword", required = false) String keyword,
            @PageableDefault(size = 5, page = 1) Pageable pageable,
            @RequestParam(name = "sortBy", defaultValue = "latest_uploaded_time", required = false) SortBy sortBy,
            @RequestParam(name = "sortOrder", defaultValue = "desc", required = false) SortOrder sortOrder,
            @Auth Accessor accessor
    ) {
        Sort.Direction direction = Sort.Direction.fromString(sortOrder.getValue().toUpperCase());
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber() - 1,
                pageable.getPageSize(),
                Sort.by(direction, sortBy.getValue()));
        var placeInfo = placeFacade.searchPlace(keyword, pageRequest, accessor);
        var response = placeMapper.toPlaceSearchResponse(placeInfo);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/keyword")
    public ResponseEntity<PlaceKeywordResponse> getPlaceKeyword(@RequestParam String keyword) {
        var keywordsInfo = placeFacade.getPlaceKeywords(keyword);
        var response = placeMapper.toPlaceKeywordResponse(keywordsInfo);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/me")
    @MemberOnly
    public ResponseEntity<MyPlaceIdResponse> getMyPlaceId(@Auth Accessor accessor) {
        var placeIds = placeFacade.getMyPlaceIds(accessor.getMemberId());
        var response = new MyPlaceIdResponse(placeIds);
        return ResponseEntity.ok().body(response);
    }
}
