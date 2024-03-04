package kr.co.yigil.region.interfaces.controller;

import kr.co.yigil.auth.Auth;
import kr.co.yigil.auth.MemberOnly;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.region.interfaces.dto.response.RegionSelectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/regions")
public class RegionApiController {

//    @GetMapping("/select")
//    @MemberOnly
//    public ResponseEntity<RegionSelectResponse> getRegionSelect(@Auth final Accessor accessor) {
//        Long memberId = accessor.getMemberId();
////        var regionInfo = regionFacade.getRegionSelect(memberId);
////        var response = regionMapper.toRegionSelectResponse(regionInfo);
//        return ResponseEntity.ok().body(response);
//    }
}
