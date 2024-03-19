package kr.co.yigil.place.application;

import kr.co.yigil.place.domain.Place;
import kr.co.yigil.place.domain.PlaceCommand;
import kr.co.yigil.place.domain.PlaceService;
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


    @Test
    void getPlaces() {
        Page<Place> mockPage = mock(Page.class);
        when(placeService.getPlaces(any(PageRequest.class))).thenReturn(mockPage);

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Place> result = placeFacade.getPlaces(pageRequest);

        assertEquals(mockPage, result);
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
        PlaceCommand.UpdateImageCommand command = new PlaceCommand.UpdateImageCommand();

        placeFacade.updateImage(command);

        verify(placeService).updateImage(command);
    }
}