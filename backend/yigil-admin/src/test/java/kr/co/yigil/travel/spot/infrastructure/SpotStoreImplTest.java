package kr.co.yigil.travel.spot.infrastructure;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.infrastructure.SpotRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SpotStoreImplTest {

    @Mock
    private SpotRepository spotRepository;
    @InjectMocks
    private SpotStoreImpl spotStore;

    @DisplayName("deleteSpot 메서드가 SpotStore를 잘 호출하는지")
    @Test
    void deleteSpot() {
        Spot mock = mock(Spot.class);

        spotStore.deleteSpot(mock);

        verify(spotRepository).delete(mock);

    }
}