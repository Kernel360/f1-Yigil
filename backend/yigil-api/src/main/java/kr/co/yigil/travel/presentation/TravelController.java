package kr.co.yigil.travel.presentation;

import kr.co.yigil.auth.Auth;
import kr.co.yigil.auth.MemberOnly;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.travel.application.TravelService;
import kr.co.yigil.travel.dto.response.PrivateUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TravelController {
    private final TravelService travelService;

    @PostMapping("/api/v1/travels/toggle-private/{travel_id}")
    @MemberOnly
    public ResponseEntity<PrivateUpdateResponse> togglePrivate(
        @PathVariable("travel_id") Long travelId,
        @Auth final Accessor accessor)
    {
        PrivateUpdateResponse response = travelService.togglePrivate(travelId, accessor.getMemberId());
        return ResponseEntity.ok().body(response);
    }
}
