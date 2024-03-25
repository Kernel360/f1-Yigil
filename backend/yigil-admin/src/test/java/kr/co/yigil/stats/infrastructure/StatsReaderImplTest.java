package kr.co.yigil.stats.infrastructure;

import kr.co.yigil.favor.domain.DailyFavorCount;
import kr.co.yigil.favor.infrastructure.DailyFavorCountRepository;
import kr.co.yigil.region.domain.DailyRegion;
import kr.co.yigil.region.infrastructure.DailyRegionRepository;
import kr.co.yigil.travel.domain.Travel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class StatsReaderImplTest {

    @InjectMocks
    private StatsReaderImpl statsReader;
    @Mock
    private DailyRegionRepository dailyRegionRepository;
    @Mock
    private DailyFavorCountRepository dailyFavorCountRepository;

    @DisplayName("getRegionStats 메서드가 DailyRegionRepository를 잘 호출하는지")
    @Test
    void getRegionStatsTest() {
        LocalDate startDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2022, 1, 31);
        List<DailyRegion> expectedRegionStats = Collections.singletonList(new DailyRegion());
        when(dailyRegionRepository.findBetweenDates(any(LocalDate.class), any(LocalDate.class))).thenReturn(expectedRegionStats);

        List<DailyRegion> actualRegionStats = statsReader.getRegionStats(startDate, endDate);

        assertEquals(expectedRegionStats, actualRegionStats);
    }

//    @DisplayName("특정 기간 동안의 일별 좋아요 수 조회가 잘 되는지 확인한다.")
//    @Test
//    void readDailyFavorCountBetween() {
//
//        DailyTotalCount dailyTotalCount = new DailyFavorCount(5L, mock(Travel.class), null, null);
//        when(dailyFavorCountRepository.findTotalLikesPerDay(any()).thenReturn(new PageImpl<>(List.of(dailyTotalCount)));
//
//        var result = statsReader.readDailyFavorCountBetween(null, null, null, null);
//
//        assertThat(result).isInstanceOf(PageImpl.class);
//    }


    @DisplayName("특정 기간 동안의 상위 N개의 일별 좋아요 수 조회가 잘 되는지 확인한다.")
    @Test
    void getTopDailyFavorCount() {
        DailyFavorCount dailyFavorCount = new DailyFavorCount(5L, mock(Travel.class), null, null);
        when(dailyFavorCountRepository.findTopByTravelTypeOrderByCountDesc(any(), any(), any(), any())).thenReturn(List.of(dailyFavorCount));

        var result = statsReader.getTopDailyFavorCount(null, null, null, null);

        assertThat(result).isInstanceOf(List.class);
    }
}