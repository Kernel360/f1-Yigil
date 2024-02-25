package kr.co.yigil.travel.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.travel.domain.Travel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TravelReaderImplTest {

    @Mock
    private TravelRepository travelRepository;

    @InjectMocks
    private TravelReaderImpl travelReader;

    @DisplayName("getTravel 메서드가 Travel이 존재할 때 값을 잘 반환하는지")
    @Test
    void getTravel_ReturnsTravel() {
        Long travelId = 1L;
        Travel expectedTravel = mock(Travel.class);
        when(travelRepository.findById(travelId)).thenReturn(Optional.of(expectedTravel));

        Travel result = travelReader.getTravel(travelId);

        assertEquals(expectedTravel, result);
    }

    @DisplayName("getTravel 메서드가 Travel이 존재하지 않을 때 예외를 잘 발생시키는지")
    @Test
    void getTravel_ThrowsBadRequestException_WhenNotFound() {
        Long travelId = 1L;
        when(travelRepository.findById(travelId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(BadRequestException.class, () -> travelReader.getTravel(travelId));

        assertEquals(ExceptionCode.NOT_FOUND_TRAVEL_ID.getMessage(), exception.getMessage());
    }
}
