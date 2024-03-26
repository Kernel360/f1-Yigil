package kr.co.yigil.stats.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import kr.co.yigil.region.domain.DailyRegion;
import kr.co.yigil.stats.domain.StaticInfo;
import kr.co.yigil.stats.domain.StatsInfo;
import kr.co.yigil.stats.domain.StatsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatsFacadeTest {

    @InjectMocks
    private StatsFacade statsFacade;

    @Mock
    private StatsService statsService;

    @DisplayName("getRegionStats 메서드가 StatsService를 잘 호출하는지")
    @Test
    void getRegionStatsTest() {
        LocalDate startDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2022, 1, 31);
        List<DailyRegion> expectedRegionStats = Collections.singletonList(new DailyRegion());
        when(statsService.getRegionStats(any(LocalDate.class), any(LocalDate.class))).thenReturn(expectedRegionStats);

        List<DailyRegion> actualRegionStats = statsFacade.getRegionStats(startDate, endDate);

        assertEquals(expectedRegionStats, actualRegionStats);
    }

    @DisplayName("getRecentRegionStats 메서드가 StatsService를 잘 호출하는지")
    @Test
    void getRecentRegionStatsTest() {
        StatsInfo.Recent expectedRecentRegionStats = mock(StatsInfo.Recent.class);
        when(statsService.getRecentRegionStats()).thenReturn(expectedRecentRegionStats);

        StatsInfo.Recent actualRecentRegionStats = statsFacade.getRecentRegionStats();

        assertEquals(expectedRecentRegionStats, actualRecentRegionStats);
    }

    @DisplayName("getDailyFavors 메서드가 StatsService를 잘 호출하는지")
    @Test
    void getDailyFavorsTest() {
        LocalDate startDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2022, 1, 31);
        StaticInfo.DailyTotalFavorCountInfo expectedDailyFavors = mock(StaticInfo.DailyTotalFavorCountInfo.class);
        when(statsService.getDailyFavors(any(LocalDate.class), any(LocalDate.class))).thenReturn(expectedDailyFavors);

        StaticInfo.DailyTotalFavorCountInfo actualDailyFavors = statsFacade.getDailyFavors(startDate, endDate);

        assertEquals(expectedDailyFavors, actualDailyFavors);
    }

    @DisplayName("getTopDailyFavors 메서드가 StatsService를 잘 호출하는지")
    @Test
    void getTopDailyFavorsTest() {
        LocalDate startDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2022, 1, 31);
        StaticInfo.DailyTravelsFavorCountInfo expectedTopDailyFavors = mock(StaticInfo.DailyTravelsFavorCountInfo.class);
        when(statsService.getTopDailyFavors(any(LocalDate.class), any(LocalDate.class))).thenReturn(expectedTopDailyFavors);

        StaticInfo.DailyTravelsFavorCountInfo actualTopDailyFavors = statsFacade.getTopDailyFavors(startDate, endDate);

        assertEquals(expectedTopDailyFavors, actualTopDailyFavors);
    }
}