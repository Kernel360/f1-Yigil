package kr.co.yigil.travel.interfaces.controller;

import kr.co.yigil.auth.Auth;
import kr.co.yigil.auth.MemberOnly;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.travel.application.SpotFacade;
import kr.co.yigil.travel.domain.spot.SpotInfo.MySpotsResponse;
import kr.co.yigil.travel.interfaces.dto.SpotDetailInfoDto;
import kr.co.yigil.travel.interfaces.dto.mapper.SpotMapper;
import kr.co.yigil.travel.interfaces.dto.request.SpotRegisterRequest;
import kr.co.yigil.travel.interfaces.dto.request.SpotUpdateRequest;
import kr.co.yigil.travel.interfaces.dto.response.SpotDeleteResponse;
import kr.co.yigil.travel.interfaces.dto.response.SpotRegisterResponse;
import kr.co.yigil.travel.interfaces.dto.response.SpotUpdateResponse;
import kr.co.yigil.travel.interfaces.dto.response.SpotsInPlaceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/spots")
public class SpotApiController {
    private final SpotFacade spotFacade;

    private final SpotMapper spotMapper;

    @GetMapping("/place/{placeId}")
    public ResponseEntity<SpotsInPlaceResponse> getSpotsInPlace(
            @PathVariable("placeId") Long placeId,
            @PageableDefault(size = 5)Pageable pageable,
            @RequestParam(name = "sortBy", defaultValue = "createdAt", required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = "desc", required = false) String sortOrder
    ) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        var result = spotFacade.getSpotSliceInPlace(placeId, pageRequest);
        SpotsInPlaceResponse response = spotMapper.spotsSliceToSpotInPlaceResponse(result);
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
    public ResponseEntity<kr.co.yigil.travel.interfaces.dto.response.MySpotsResponse> getMySpotList(
        @Auth final Accessor accessor,
        @PageableDefault(size = 5) Pageable pageable,
        @RequestParam(name = "sortBy", defaultValue = "createdAt", required = false) String sortBy,
        @RequestParam(name = "sortOrder", defaultValue = "desc", required = false) String sortOrder,
        @RequestParam(name = "selected", defaultValue = "all", required = false) String visibility
    ) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
            Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        final MySpotsResponse mySpotsResponse = spotFacade.getMemberSpotsInfo(
            accessor.getMemberId(), pageRequest, visibility);
        var response = spotMapper.of(mySpotsResponse);
        return ResponseEntity.ok().body(response);
    }

}
