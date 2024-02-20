package kr.co.yigil.travel.interfaces.controller;

import kr.co.yigil.auth.Auth;
import kr.co.yigil.auth.MemberOnly;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.travel.application.SpotFacade;
import kr.co.yigil.travel.interfaces.dto.mapper.SpotMapper;
import kr.co.yigil.travel.interfaces.dto.mapper.SpotRegisterMapper;
import kr.co.yigil.travel.interfaces.dto.request.SpotRegisterRequest;
import kr.co.yigil.travel.interfaces.dto.response.SpotRegisterResponse;
import kr.co.yigil.travel.interfaces.dto.response.SpotsInPlaceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/spots")
public class SpotApiController {

    private final SpotFacade spotFacade;

    private final SpotMapper spotMapper;
    private final SpotRegisterMapper spotRegisterMapper;
    @GetMapping("/place/{place_id}")
    public ResponseEntity<SpotsInPlaceResponse> getSpotsInPlace(
            @PathVariable("place_id") Long placeId,
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
        var spotCommand = spotRegisterMapper.toRegisterSpotRequest(request);
        spotFacade.registerSpot(spotCommand, memberId);
        return ResponseEntity.ok().body(new SpotRegisterResponse("Spot 생성 완료"));
    }
}
