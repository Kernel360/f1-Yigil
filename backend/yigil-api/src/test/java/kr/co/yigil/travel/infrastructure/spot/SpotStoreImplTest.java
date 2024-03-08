package kr.co.yigil.travel.infrastructure.spot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.infrastructure.SpotRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SpotStoreImplTest {

    @Mock
    private SpotRepository spotRepository;

    @InjectMocks
    private SpotStoreImpl spotStore;

    @DisplayName("store가 저장된 Spot을 잘 반환하는지")
    @Test
    void store_SavesAndReturnsSpot() {
        Spot spot = mock(Spot.class); // 가정: Spot 객체를 적절히 초기화
        when(spotRepository.save(spot)).thenReturn(spot);

        Spot savedSpot = spotStore.store(spot);

        assertEquals(spot, savedSpot);
        verify(spotRepository).save(spot);
    }

    @DisplayName("remove 메서드가 Spot을 잘 삭제하는지")
    @Test
    void remove_DeleteSpot() {
        Spot spot = mock(Spot.class);
        spotStore.remove(spot);
        verify(spotRepository).delete(spot);
    }
}
