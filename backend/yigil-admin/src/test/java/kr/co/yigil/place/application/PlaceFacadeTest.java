package kr.co.yigil.place.application;

import java.util.List;
import kr.co.yigil.place.domain.Place;
import kr.co.yigil.place.domain.PlaceCommand;
import kr.co.yigil.place.domain.PlaceInfo.Map;
import kr.co.yigil.place.domain.PlaceService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlaceFacadeTest {

    @Mock
    private PlaceService placeService;

    @InjectMocks
    private PlaceFacade placeFacade;


    @DisplayName("getPlaces 메서드가 잘 동작하는지")
    @Test
    void getPlaces() {
        Map mockMap = mock(Map.class);
        when(placeService.getPlaces(any(PlaceCommand.PlaceMapCommand.class))).thenReturn(List.of(mockMap));

        List<Map> result = placeFacade.getPlaces(new PlaceCommand.PlaceMapCommand(1, 1, 1, 1));

        assertEquals(List.of(mockMap), result);
    }

    @Test
    void getPlaceDetail() {
        Place mockPlace = mock(Place.class);
        when(placeService.getPlaceDetail(anyLong())).thenReturn(mockPlace);

        Place result = placeFacade.getPlaceDetail(1L);

        assertEquals(mockPlace, result);
    }

    @Test
    void updateImage() {
        PlaceCommand.UpdateImageCommand command = mock(PlaceCommand.UpdateImageCommand.class);

        placeFacade.updateImage(command);

        verify(placeService).updateImage(command);
    }
}