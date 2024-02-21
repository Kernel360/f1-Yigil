package kr.co.yigil.travel.presentation;

import java.net.URI;
import kr.co.yigil.auth.Auth;
import kr.co.yigil.auth.MemberOnly;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.travel.application.SpotService;
import kr.co.yigil.travel.interfaces.dto.request.SpotCreateRequest;
import kr.co.yigil.travel.interfaces.dto.request.SpotUpdateRequest;
import kr.co.yigil.travel.interfaces.dto.response.SpotCreateResponse;
import kr.co.yigil.travel.interfaces.dto.response.SpotDeleteResponse;
import kr.co.yigil.travel.interfaces.dto.response.SpotFindDto;
import kr.co.yigil.travel.interfaces.dto.response.SpotInfoResponse;
import kr.co.yigil.travel.interfaces.dto.response.SpotUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
public class    SpotController {

    private final SpotService spotService;


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
