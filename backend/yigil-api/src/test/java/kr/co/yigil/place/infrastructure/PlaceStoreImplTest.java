package kr.co.yigil.place.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import kr.co.yigil.place.domain.Place;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PlaceStoreImplTest {

    @Mock
    private PlaceRepository placeRepository;

    @InjectMocks
    private PlaceStoreImpl placeStore;

    @DisplayName("store가 저장된 Place을 잘 반환하는지")
    @Test
    void store_SavesAndReturnPlace() {
        Place place = mock(Place.class);
        when(placeRepository.save(place)).thenReturn(place);

        Place savedPlace = placeStore.store(place);

        assertEquals(place, savedPlace);
        verify(placeRepository).save(place);
    }
}
