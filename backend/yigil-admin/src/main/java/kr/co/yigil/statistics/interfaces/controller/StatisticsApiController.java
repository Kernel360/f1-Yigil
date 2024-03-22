package kr.co.yigil.statistics.interfaces.controller;

import kr.co.yigil.statistics.application.StatisticsFacade;
import kr.co.yigil.statistics.interfaces.dto.StatisticsDto;
import kr.co.yigil.statistics.interfaces.dto.StatisticsMapper;
import kr.co.yigil.travel.TravelType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class StatisticsApiController {
    private final StatisticsFacade statisticsFacade;
    private final StatisticsMapper statisticsMapper;

    @GetMapping("/api/v1/statistics/daily-favors")
    public ResponseEntity<StatisticsDto.DailyFavorsResponse> getDailyFavors(
            @PageableDefault Pageable pageable,
            @RequestParam(name = "travelType", defaultValue = "spot") TravelType travelType,
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        if (startDate == null) {
            startDate = LocalDate.now().minusDays(1);
        }
        if (endDate == null) {
            endDate = LocalDate.now(); // today
        }
        var info = statisticsFacade.getDailyFavors(startDate, endDate, travelType, pageable);
        var response = statisticsMapper.toDailyFavorsResponse(info);
        return ResponseEntity.ok(response);
    }
}
