package kr.co.yigil.travel.spot.infrastructure;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.infrastructure.SpotRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class SpotReaderImplTest {

    @Mock
    private SpotRepository spotRepository;
    @InjectMocks
    private SpotReaderImpl spotReader;

    @DisplayName("getSpots 메서드가 SpotReader를 잘 호출하는지")
    @Test
    void getSpots() {
        Spot spot = mock(Spot.class);
        List<Spot> spots = List.of(spot);
        PageRequest pageable = PageRequest.of(0, 5);
        Page<Spot> pageSpots = new PageImpl<>(spots);
        when(spotRepository.findAll(pageable)).thenReturn(pageSpots);

        var response = spotReader.getSpots(pageable);

        assertThat(response).isEqualTo(pageSpots);
    }

    @DisplayName("getSpot 메서드가 SpotReader를 잘 호출하는지")
    @Test
    void getSpot() {
        Spot spot = mock(Spot.class);
        Long spotId = 1L;
        when(spotRepository.findById(spotId)).thenReturn(java.util.Optional.of(spot));

        var response = spotReader.getSpot(spotId);

        assertThat(response).isEqualTo(spot);
    }
}