package kr.co.yigil.travel.interfaces.controller;

import kr.co.yigil.auth.Auth;
import kr.co.yigil.auth.MemberOnly;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.global.Selected;
import kr.co.yigil.global.SortBy;
import kr.co.yigil.global.SortOrder;
import kr.co.yigil.travel.application.SpotFacade;
import kr.co.yigil.travel.domain.spot.SpotInfo;
import kr.co.yigil.travel.domain.spot.SpotInfo.MySpotsResponse;
import kr.co.yigil.travel.interfaces.dto.SpotDetailInfoDto;
import kr.co.yigil.travel.interfaces.dto.mapper.SpotMapper;
import kr.co.yigil.travel.interfaces.dto.request.SpotRegisterRequest;
import kr.co.yigil.travel.interfaces.dto.request.SpotUpdateRequest;
import kr.co.yigil.travel.interfaces.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/spots")
public class SpotApiController {

    private final SpotFacade spotFacade;

    private final SpotMapper spotMapper;

    @GetMapping("/place/{placeId}")
    public ResponseEntity<SpotsInPlaceResponse> getSpotsInPlace(
            @PathVariable("placeId") Long placeId,
            @Auth Accessor accessor,
            @PageableDefault(size = 5, page = 1) Pageable pageable,
            @RequestParam(name = "sortBy", defaultValue = "created_at", required = false) SortBy sortBy,
            @RequestParam(name = "sortOrder", defaultValue = "desc", required = false) SortOrder sortOrder
    ) {
        Sort.Direction direction = Sort.Direction.fromString(sortOrder.getValue().toUpperCase());
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber() - 1,
                pageable.getPageSize(),
                Sort.by(direction, sortBy.getValue()));
        var result = spotFacade.getSpotSliceInPlace(placeId, accessor, pageRequest);
        SpotsInPlaceResponse response = spotMapper.toSpotsInPlaceResponse(result);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/place/{placeId}/me")
    @MemberOnly
    public ResponseEntity<MySpotInPlaceResponse> getMySpotInPlace(
            @PathVariable("placeId") Long placeId,
            @Auth Accessor accessor
    ) {
        Long memberId = accessor.getMemberId();
        var spotInfo = spotFacade.retrieveMySpotInfoInPlace(placeId, memberId);
        var response = spotMapper.toMySpotInPlaceResponse(spotInfo);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    @MemberOnly
    public ResponseEntity<SpotRegisterResponse> registerSpot(
            @ModelAttribute SpotRegisterRequest request,
            @Auth final Accessor accessor
    ) {
        Long memberId = accessor.getMemberId();
        var spotCommand = spotMapper.toRegisterSpotRequest(request);
        spotFacade.registerSpot(spotCommand, memberId);
        return ResponseEntity.ok().body(new SpotRegisterResponse("Spot 생성 완료"));
    }

    @GetMapping("/{spotId}")
    public ResponseEntity<SpotDetailInfoDto> retrieveSpot(@PathVariable("spotId") Long spotId) {
        var spotInfo = spotFacade.retrieveSpotInfo(spotId);
        var response = spotMapper.toSpotDetailInfoDto(spotInfo);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/{spotId}")
    @MemberOnly
    public ResponseEntity<SpotUpdateResponse> updateSpot(
            @PathVariable("spotId") Long spotId,
            @ModelAttribute SpotUpdateRequest request,
            @Auth final Accessor accessor
    ) {
        Long memberId = accessor.getMemberId();
        var spotCommand = spotMapper.toModifySpotRequest(request);
        spotFacade.modifySpot(spotCommand, spotId, memberId);
        return ResponseEntity.ok().body(new SpotUpdateResponse("Spot 수정 완료"));
    }

    @DeleteMapping("/{spotId}")
    @MemberOnly
    public ResponseEntity<SpotDeleteResponse> deleteSpot(
            @PathVariable("spotId") Long spotId,
            @Auth final Accessor accessor
    ) {
        Long memberId = accessor.getMemberId();
        spotFacade.deleteSpot(spotId, memberId);
        return ResponseEntity.ok().body(new SpotDeleteResponse("Spot 삭제 완료"));
    }

    @GetMapping("/my")
    @MemberOnly
    public ResponseEntity<MySpotsResponseDto> getMySpotList(
            @Auth final Accessor accessor,
            @PageableDefault(size = 5, page = 1) Pageable pageable,
            @RequestParam(name = "sortBy", defaultValue = "created_at", required = false) SortBy sortBy,
            @RequestParam(name = "sortOrder", defaultValue = "desc", required = false) SortOrder sortOrder,
            @RequestParam(name = "selected", defaultValue = "all", required = false) Selected visibility
    ) {
        Sort.Direction direction = Sort.Direction.fromString(sortOrder.getValue().toUpperCase());
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber() - 1,
                pageable.getPageSize(),
                Sort.by(direction, sortBy.getValue()));
        final MySpotsResponse mySpotsResponse = spotFacade.getMemberSpotsInfo(
                accessor.getMemberId(), visibility, pageRequest);
        var response = spotMapper.of(mySpotsResponse);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/my/favorite")
    @MemberOnly
    public ResponseEntity<MyFavoriteSpotsResponse> getMyFavoriteSpots(
            @Auth final Accessor accessor,
            @PageableDefault(size = 5, page = 1) Pageable pageable,
            @RequestParam(name = "sortBy", defaultValue = "place_name", required = false) SortBy sortBy,
            @RequestParam(name = "sortOrder", defaultValue = "asc", required = false) SortOrder sortOrder
    ) {
        Sort.Direction direction = Sort.Direction.fromString(sortOrder.getValue().toUpperCase());
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber() - 1,
                pageable.getPageSize(),
                Sort.by(direction, sortBy.getValue()));
        final SpotInfo.MyFavoriteSpotsInfo myFavoriteSpotsInfo = spotFacade.getFavoriteSpotsInfo(
                accessor.getMemberId(), pageRequest);
        MyFavoriteSpotsResponse response = spotMapper.of(myFavoriteSpotsInfo);
        return ResponseEntity.ok().body(response);
    }
}
