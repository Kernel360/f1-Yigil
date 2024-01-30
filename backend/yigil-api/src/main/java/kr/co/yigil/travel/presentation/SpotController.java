package kr.co.yigil.travel.presentation;

import java.net.URI;
import kr.co.yigil.auth.Auth;
import kr.co.yigil.auth.MemberOnly;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.travel.application.SpotService;
import kr.co.yigil.travel.dto.request.SpotCreateRequest;
import kr.co.yigil.travel.dto.request.SpotUpdateRequest;
import kr.co.yigil.travel.dto.response.SpotCreateResponse;
import kr.co.yigil.travel.dto.response.SpotDeleteResponse;
import kr.co.yigil.travel.dto.response.SpotInfoResponse;
import kr.co.yigil.travel.dto.response.SpotListResponse;
import kr.co.yigil.travel.dto.response.SpotUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<SpotListResponse> getSpotList(
            @PathVariable("place_id") Long placeId
    ) {
        SpotListResponse spotListResponse = spotService.getSpotList(placeId);
        return ResponseEntity.ok().body(spotListResponse);
    }

    @PostMapping
    @MemberOnly
    public ResponseEntity<SpotCreateResponse> createSpot(
            @ModelAttribute SpotCreateRequest spotCreateRequest,
            @Auth final Accessor accessor
    ) {
        SpotCreateResponse spotCreateResponse = spotService.createSpot(accessor.getMemberId(),
                spotCreateRequest);
        URI uri = URI.create("/api/v1/spots/" + spotCreateResponse.getSpotId());
        return ResponseEntity.created(uri)
                .body(spotCreateResponse);
    }

    @GetMapping("/{spot_id}")
    public ResponseEntity<SpotInfoResponse> getSpotInfo(
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
        URI uri = URI.create("/api/v1/spots/" + spotId);
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .location(uri)
                .body(spotUpdateResponse);
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
