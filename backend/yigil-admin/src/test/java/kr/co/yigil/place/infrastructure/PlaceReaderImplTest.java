package kr.co.yigil.place.infrastructure;

import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.place.domain.Place;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlaceReaderImplTest {

    @Mock
    private PlaceRepository placeRepository;

    @InjectMocks
    private PlaceReaderImpl placeReader;

    @Test
    void getPlaces() {
        Page<Place> page = new PageImpl<>(List.of(mock(Place.class)));
        when(placeRepository.findAll(any(Pageable.class))).thenReturn(page);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Place> result = placeReader.getPlaces(pageable);

        assertEquals(page, result);
    }

    @Test
    void getPlace() {
        Place place = mock(Place.class);
        when(placeRepository.findById(anyLong())).thenReturn(Optional.of(place));

        Place result = placeReader.getPlace(1L);

        assertEquals(place, result);
    }

    @Test
    void whenGetPlace_givenInvalidLongId_thenShouldThrowError() {
        when(placeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () ->placeReader.getPlace(1L));


    }
}