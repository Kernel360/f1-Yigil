package kr.co.yigil.stats.interfaces.controller;

import java.time.LocalDate;
import java.util.List;
import kr.co.yigil.region.domain.DailyRegion;
import kr.co.yigil.stats.application.StatsFacade;
import kr.co.yigil.stats.domain.StatsInfo;
import kr.co.yigil.stats.domain.StatsInfo.Recent;
import kr.co.yigil.stats.interfaces.dto.mapper.StatsMapper;
import kr.co.yigil.stats.interfaces.dto.response.RecentRegionStatsResponse;
import kr.co.yigil.stats.interfaces.dto.response.RegionStatsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stats")
public class StatsApiController {
    private final StatsFacade statsFacade;
    private final StatsMapper statsMapper;


    @GetMapping("/region")
    public ResponseEntity<RegionStatsResponse> getRegionStats(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<DailyRegion> regionStats = statsFacade.getRegionStats(startDate, endDate);
        RegionStatsResponse response = statsMapper.toResponse(regionStats);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/region/recent")
    public ResponseEntity<RecentRegionStatsResponse> getRecentRegionStats() {
        Recent recent = statsFacade.getRecentRegionStats();
        RecentRegionStatsResponse response = statsMapper.toRecentRegionStatsResponse(recent);
        return ResponseEntity.ok(response);
    }

}
