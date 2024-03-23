package kr.co.yigil.travel.infrastructure.spot;

import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.infrastructure.SpotRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SpotReaderImplTest {

    @Mock
    private SpotRepository spotRepository;

    @InjectMocks
    private SpotReaderImpl spotReader;

    @DisplayName("getSpot 메서드가 Spot을 잘 반환하는지")
    @Test
    void getSpot_ReturnsSpot() {
        Long spotId = 1L;
        Spot expectedSpot = mock(Spot.class);
        when(spotRepository.findById(spotId)).thenReturn(Optional.of(expectedSpot));

        Spot result = spotReader.getSpot(spotId);

        assertEquals(expectedSpot, result);
    }

    @DisplayName("getSpot 메서드가 SpotId가 유효하지 않을 때 예외를 잘 발생시키는지")
    @Test
    void getSpot_ThrowsBadRequestException_WhenNotFound() {
        Long spotId = 1L;
        when(spotRepository.findById(spotId)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> spotReader.getSpot(spotId));
    }

    @DisplayName("findByPlaceIdAndMemberId 메서드가 Spot의 Optional 객체를 잘 반환하는지")
    @Test
    void findByPlaceIdAndMemberId_ReturnsOptionalOfSpot() {
        Optional<Spot> expected = mock(Optional.class);
        when(spotRepository.findTopByPlaceIdAndMemberId(anyLong(), anyLong())).thenReturn(expected);

        Optional<Spot> result = spotReader.findSpotByPlaceIdAndMemberId(1L, 1L);

        assertEquals(expected, result);
    }

    @DisplayName("getSpots 메서드가 Spot List를 잘 반환하는지")
    @Test
    void getSpots_ReturnsListOfSpots() {
        List<Long> spotIds = Arrays.asList(1L, 2L);
        Spot spot1 = mock(Spot.class);
        Spot spot2 = mock(Spot.class);
        when(spotRepository.findById(1L)).thenReturn(Optional.of(spot1));
        when(spotRepository.findById(2L)).thenReturn(Optional.of(spot2));

        List<Spot> result = spotReader.getSpots(spotIds);

        assertEquals(2, result.size());
        assertTrue(result.containsAll(Arrays.asList(spot1, spot2)));
    }

    @DisplayName("getSpotSliceInPlace 메서드가 Spot의 Slice를 잘 반환하는지")
    @Test
    void getSpotSliceInPlace_ReturnsSliceOfSpots() {
        Long placeId = 1L;
        Pageable pageable = mock(Pageable.class);
        Slice<Spot> expectedSlice = mock(Slice.class);
        when(spotRepository.findAllByPlaceIdAndIsInCourseIsFalseAndIsPrivateIsFalse(placeId, pageable)).thenReturn(expectedSlice);

        Slice<Spot> result = spotReader.getSpotSliceInPlace(placeId, pageable);

        assertEquals(expectedSlice, result);
    }

    @DisplayName("getSpotCountInPlace 메서드가 count를 잘 반환하는지")
    @Test
    void getSpotCountInPlace_ReturnsCount() {
        Long placeId = 1L;
        int expectedCount = 5;
        when(spotRepository.countByPlaceId(placeId)).thenReturn(expectedCount);

        int result = spotReader.getSpotCountInPlace(placeId);

        assertEquals(expectedCount, result);
    }

    @Test
    void isExistSpot_ReturnsTrue_WhenSpotExists() {
        Long spotId = 1L;
        Long memberId = 1L;
        when(spotRepository.existsByIdAndMemberId(spotId, memberId)).thenReturn(true);

        boolean result = spotReader.isExistSpot(spotId, memberId);

        assertTrue(result);
    }

    @Test
    void isExistSpot_ReturnsFalse_WhenSpotDoesNotExist() {
        Long spotId = 1L;
        Long memberId = 1L;
        when(spotRepository.existsByIdAndMemberId(spotId, memberId)).thenReturn(false);

        boolean result = spotReader.isExistSpot(spotId, memberId);

        assertFalse(result);
    }
}
