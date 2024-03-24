package kr.co.yigil.favor.infrastructure;

import kr.co.yigil.favor.domain.DailyFavorCount;
import kr.co.yigil.travel.domain.Travel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class DailyFavorCountReaderImplTest {

    @Mock
    private DailyFavorCountRepository dailyFavorCountRepository;
    @InjectMocks
    private DailyFavorCountReaderImpl dailyFavorCountReader;

    @DisplayName("특정 기간 동안의 일별 좋아요 수 조회가 잘 되는지 확인한다.")
    @Test
    void readDailyFavorCountBetween() {

        DailyFavorCount dailyFavorCount = new DailyFavorCount(5L, mock(Travel.class), null, null);
        when(dailyFavorCountRepository.findAllByCreatedAtBetweenAndTravelTypeOrAllOrderByCountDesc(any(), any(), any(), any())).thenReturn(new PageImpl<>(List.of(dailyFavorCount)));

        var result = dailyFavorCountReader.readDailyFavorCountBetween(null, null, null, null);

        assertThat(result).isInstanceOf(PageImpl.class);
    }


    @DisplayName("특정 기간 동안의 상위 N개의 일별 좋아요 수 조회가 잘 되는지 확인한다.")
    @Test
    void getTopDailyFavorCount() {
        DailyFavorCount dailyFavorCount = new DailyFavorCount(5L, mock(Travel.class), null, null);
        when(dailyFavorCountRepository.findTopByTravelTypeOrderByCountDesc(any(), any(), any(), any())).thenReturn(List.of(dailyFavorCount));

        var result = dailyFavorCountReader.getTopDailyFavorCount(null, null, null, null);

        assertThat(result).isInstanceOf(List.class);
    }
}