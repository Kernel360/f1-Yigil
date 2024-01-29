package kr.co.yigil.travel.presentation;

import kr.co.yigil.auth.Auth;
import kr.co.yigil.auth.MemberOnly;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.travel.application.SpotService;
import kr.co.yigil.travel.dto.request.SpotCreateRequest;
import kr.co.yigil.travel.dto.request.SpotUpdateRequest;
import kr.co.yigil.travel.dto.response.SpotCreateResponse;
import kr.co.yigil.travel.dto.response.SpotDeleteResponse;
import kr.co.yigil.travel.dto.response.SpotFindListResponse;
import kr.co.yigil.travel.dto.response.SpotFindResponse;
import kr.co.yigil.travel.dto.response.SpotUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/spots")
public class SpotController {

    private final SpotService spotService;

    @GetMapping("/places/{place_id}")
    public ResponseEntity<SpotFindListResponse> getSpotList(
            @PathVariable("place_id") Long placeId
    ) {
        SpotFindListResponse spotFindListResponse = spotService.getSpotList(placeId);
        return ResponseEntity.ok().body(spotFindListResponse);
    }

    @PostMapping
    @MemberOnly
    public ResponseEntity<SpotCreateResponse> createSpot(
            @ModelAttribute SpotCreateRequest spotCreateRequest,
            @Auth final Accessor accessor
    ) {
        SpotCreateResponse spotCreateResponse = spotService.createSpot(accessor.getMemberId(),
                spotCreateRequest);
        return ResponseEntity.ok().body(spotCreateResponse);
    }

    @GetMapping("/{spot_id}")
    public ResponseEntity<SpotFindResponse> getSpot(
            @PathVariable("spot_id") Long spotId
    ) {
        SpotFindResponse spotFindResponse = spotService.getSpot(spotId);
        return ResponseEntity.ok().body(spotFindResponse);
    }


    @PostMapping("/{spot_id}")
    @MemberOnly
    public ResponseEntity<SpotUpdateResponse> updateSpot(
            @PathVariable("spot_id") Long spotId,
            @Auth final Accessor accessor,
            @ModelAttribute SpotUpdateRequest spotUpdateRequest
    ) {
        SpotUpdateResponse spotUpdateResponse = spotService.updateSpot(accessor.getMemberId(),
                spotId, spotUpdateRequest);
        return ResponseEntity.ok().body(spotUpdateResponse);
    }

    @DeleteMapping("/{spot_id}")
    @MemberOnly
    public ResponseEntity<SpotDeleteResponse> deleteSpot(
            @PathVariable("spot_id") Long spotId,
            @Auth final Accessor accessor
    ) {
        SpotDeleteResponse spotDeleteResponse = spotService.deleteSpot(accessor.getMemberId(),
                spotId);
        return ResponseEntity.ok().body(spotDeleteResponse);
    }

}
