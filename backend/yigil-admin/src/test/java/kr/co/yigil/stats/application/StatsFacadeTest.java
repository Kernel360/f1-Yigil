package kr.co.yigil.stats.application;

import kr.co.yigil.region.domain.DailyRegion;
import kr.co.yigil.stats.domain.StaticInfo;
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


    @DisplayName("일별 좋아요 수를 조회한다.")
    @Test
    void getDailyFavors() {
        PageRequest pageable = PageRequest.of(0, 10);
        when(statsService.getDailyFavors(any(PageRequest.class))).thenReturn(mock(StaticInfo.DailyTotalFavorCountInfo.class));

        var result = statsFacade.getDailyFavors(pageable);

        assertThat(result).isInstanceOf(StaticInfo.DailyTotalFavorCountInfo.class);
    }


    @DisplayName("일별 좋아요 수 상위를 조회한다.")
    @Test
    void getTopDailyFavors() {
        when(statsService.getTopDailyFavors(any(), any(), any(), any())).thenReturn(mock(StaticInfo.DailyTravelsFavorCountInfo.class));

        var result = statsFacade.getTopDailyFavors(null, null, null, null);

        assertThat(result).isInstanceOf(StaticInfo.DailyTravelsFavorCountInfo.class);
    }
}