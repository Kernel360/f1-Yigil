package kr.co.yigil.stats.interfaces.controller;

import java.time.LocalDate;
import java.util.List;
import kr.co.yigil.region.domain.DailyRegion;
import kr.co.yigil.stats.application.StatsFacade;
import kr.co.yigil.stats.domain.StatsInfo;
import kr.co.yigil.stats.domain.StatsInfo.Recent;
import kr.co.yigil.stats.interfaces.dto.StatsDto;
import kr.co.yigil.stats.interfaces.dto.mapper.StatsMapper;
import kr.co.yigil.stats.interfaces.dto.response.RecentRegionStatsResponse;
import kr.co.yigil.stats.interfaces.dto.response.RegionStatsResponse;
import kr.co.yigil.travel.TravelType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stats")
public class StatsApiController {

    private final StatsFacade statsFacade;
    private final StatsMapper statsMapper;


    @GetMapping("/region")
    public ResponseEntity<StatsDto.RegionStatsResponse> getRegionStats(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<DailyRegion> regionStats = statsFacade.getRegionStats(startDate, endDate);
        StatsDto.RegionStatsResponse response = statsMapper.toResponse(regionStats);
        return ResponseEntity.ok(response);

    }

    @GetMapping("/daily-favors")
    public ResponseEntity<StatsDto.DailyTotalFavorCountResponse> getDailyFavors(
            @PageableDefault Pageable pageable
    ) {
        var info = statsFacade.getDailyFavors(pageable);
        var response = statsMapper.toDailyTotalFavorCountDto(info);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/daily-favors/top")
    public ResponseEntity<StatsDto.DailyTravelFavorsResponse> getTopDailyFavors(
            @RequestParam(name = "travelType", defaultValue = "spot") TravelType travelType,
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(name = "limit", defaultValue = "10") Integer limit
    ) {
        if (startDate == null) {
            startDate = LocalDate.now().minusDays(1);
        }
        if (endDate == null) {
            endDate = LocalDate.now();
        }
        var info = statsFacade.getTopDailyFavors(startDate, endDate, travelType, limit);
        var response = statsMapper.toDailyFavorsResponse(info);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/region/recent")
    public ResponseEntity<RecentRegionStatsResponse> getRecentRegionStats() {
        Recent recent = statsFacade.getRecentRegionStats();
        RecentRegionStatsResponse response = statsMapper.toRecentRegionStatsResponse(recent);
        return ResponseEntity.ok(response);
    }

}
