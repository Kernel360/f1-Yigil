package kr.co.yigil.travel.interfaces.controller;

import kr.co.yigil.auth.Auth;
import kr.co.yigil.auth.MemberOnly;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.travel.application.TravelFacade;
import kr.co.yigil.travel.interfaces.dto.mapper.TravelMapper;
import kr.co.yigil.travel.interfaces.dto.request.ChangeStatusTravelRequest;
import kr.co.yigil.travel.interfaces.dto.request.TravelsVisibilityChangeRequest;
import kr.co.yigil.travel.interfaces.dto.response.ChangeStatusTravelResponse;
import kr.co.yigil.travel.interfaces.dto.response.TravelsVisibilityChangeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/travels")
public class TravelApiController {
    private final TravelFacade travelFacade;
    private final TravelMapper travelMapper;

    @PutMapping("/change-on-public")
    @MemberOnly
    public ResponseEntity<ChangeStatusTravelResponse> changeOnPublicTravel(
            @RequestBody ChangeStatusTravelRequest request,
            @Auth final Accessor accessor)
    {
        travelFacade.changeOnPublicTravel(request.getTravelId(), accessor.getMemberId());
        return ResponseEntity.ok().body(new ChangeStatusTravelResponse("리뷰 공개 상태 변경 완료"));
    }

    @PutMapping("/change-on-private")
    @MemberOnly
    public ResponseEntity<ChangeStatusTravelResponse> changeOnPrivateTravel(
            @RequestBody ChangeStatusTravelRequest request,
            @Auth final Accessor accessor
    ) {
        travelFacade.changeOnPrivateTravel(request.getTravelId(), accessor.getMemberId());
        return ResponseEntity.ok().body(new ChangeStatusTravelResponse("리뷰 공개 상태 변경 완료"));
    }

    @PutMapping("/change-visibility")
    @MemberOnly
    public ResponseEntity<TravelsVisibilityChangeResponse> setTravelsVisibility(
        @Auth final Accessor accessor,
        @RequestBody TravelsVisibilityChangeRequest request
    ){
        var command = travelMapper.of(request);
        travelFacade.setTravelsVisibility(accessor.getMemberId(), command);
        var response = travelMapper.of("리뷰 공개 상태 변경 완료");
        return ResponseEntity.ok().body(response);
    }


}
