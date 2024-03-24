package kr.co.yigil.statistics.application;

import kr.co.yigil.statistics.domain.StaticInfo;
import kr.co.yigil.statistics.domain.StatisticsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatisticsFacadeTest {

    @InjectMocks
    private StatisticsFacade statisticsFacade;

    @Mock
    private StatisticsService statisticsService;

    @DisplayName("일별 좋아요 수를 조회한다.")
    @Test
    void getDailyFavors() {
        when(statisticsService.getDailyFavors(any(), any(), any(), any())).thenReturn(mock(StaticInfo.DailyFavorsInfo.class));

        var result = statisticsFacade.getDailyFavors(null, null, null, null);

        assertThat(result).isInstanceOf(StaticInfo.DailyFavorsInfo.class);
    }


    @DisplayName("일별 좋아요 수 상위를 조회한다.")
    @Test
    void getTopDailyFavors() {
        when(statisticsService.getTopDailyFavors(any(), any(), any(), any())).thenReturn(mock(StaticInfo.DailyFavorsInfo.class));

        var result = statisticsFacade.getTopDailyFavors(null, null, null, null);

        assertThat(result).isInstanceOf(StaticInfo.DailyFavorsInfo.class);
    }
}