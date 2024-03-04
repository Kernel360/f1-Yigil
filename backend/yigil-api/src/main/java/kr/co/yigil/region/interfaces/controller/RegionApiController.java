package kr.co.yigil.region.interfaces.controller;

import kr.co.yigil.auth.Auth;
import kr.co.yigil.auth.MemberOnly;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.region.application.RegionFacade;
import kr.co.yigil.region.interfaces.dto.mapper.RegionMapper;
import kr.co.yigil.region.interfaces.dto.response.MyRegionResponse;
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

    private final RegionFacade regionFacade;
    private final RegionMapper regionMapper;
    @GetMapping("/select")
    @MemberOnly
    public ResponseEntity<RegionSelectResponse> getRegionSelect(@Auth final Accessor accessor) {
        Long memberId = accessor.getMemberId();
        var regionInfo = regionFacade.getRegionSelectList(memberId);
        var response = regionMapper.toRegionSelectResponse(regionInfo);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/my")
    @MemberOnly
    public ResponseEntity<MyRegionResponse> getMyRegion(@Auth final Accessor accessor) {
        Long memberId = accessor.getMemberId();
        var myRegionInfo = regionFacade.getMyRegions(memberId);
        var response = regionMapper.toMyRegionResponse(myRegionInfo);
        return ResponseEntity.ok().body(response);
    }
}
