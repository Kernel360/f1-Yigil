package kr.co.yigil.stats.infrastructure;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import kr.co.yigil.travel.infrastructure.TravelRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TravelReaderImplTest {

    @InjectMocks
    private TravelReaderImpl travelReader;

    @Mock
    private TravelRepository travelRepository;

    @DisplayName("getTodayTravelCnt 메서드가 TravelRepository를 잘 호출하는지")
    @Test
    void getTodayTravelCntTest() {
        travelReader.getTodayTravelCnt();
        verify(travelRepository).countByCreatedAtToday();
    }

    @DisplayName("getRecentTravel 메서드가 TravelRepository를 잘 호출하는지")
    @Test
    void getRecentTravelTest() {
        travelReader.getRecentTravel();
        verify(travelRepository, times(1)).findTop5ByOrderByCreatedAtDesc();
    }



}
