package kr.co.yigil.stats.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import kr.co.yigil.member.Member;
import kr.co.yigil.region.domain.DailyRegion;
import kr.co.yigil.travel.domain.Travel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class StatsServiceImplTest {

    @InjectMocks
    private StatsServiceImpl statsService;

    @Mock
    private StatsReader statsReader;

    @Mock TravelReader travelReader;

    @DisplayName("getRegionStats 메서드가 StatsReader를 잘 호출하는지")
    @Test
    void getRegionStatsTest() {
        LocalDate startDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2022, 1, 31);
        List<DailyRegion> expectedRegionStats = Collections.singletonList(new DailyRegion());
        when(statsReader.getRegionStats(any(LocalDate.class), any(LocalDate.class))).thenReturn(expectedRegionStats);

        List<DailyRegion> actualRegionStats = statsService.getRegionStats(startDate, endDate);

        assertEquals(expectedRegionStats, actualRegionStats);
    }

    @DisplayName("getRecentRegionStats 메서드가 TravelReader를 잘 호출하는지")
    @Test
    void getRecentRegionStatsTest() {
        long travelCount = 1;
        Travel mockTravel = mock(Travel.class);
        Member mockMember= mock(Member.class);
        when(mockTravel.getMember()).thenReturn(mockMember);
        List<Travel> recentTravel = Collections.singletonList(mockTravel);
        when(travelReader.getTodayTravelCnt()).thenReturn(travelCount);
        when(travelReader.getRecentTravel()).thenReturn(recentTravel);

        StatsInfo.Recent actualRecentRegionStats = statsService.getRecentRegionStats();

        assertEquals(travelCount, actualRecentRegionStats.getTravelCnt());
        assertEquals(recentTravel.size(), actualRecentRegionStats.getTravels().size());
    }
}