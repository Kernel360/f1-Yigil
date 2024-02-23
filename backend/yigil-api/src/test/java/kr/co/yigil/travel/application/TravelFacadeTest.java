package kr.co.yigil.travel.application;

import static org.mockito.Mockito.verify;

import kr.co.yigil.travel.domain.TravelService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TravelFacadeTest {

    @Mock
    private TravelService travelService;

    @InjectMocks
    private TravelFacade travelFacade;

    @DisplayName("changeOnPublicTravel 메서드가 TravelService를 잘 호출하는지")
    @Test
    void changeOnPublicTravel_ShouldCallService() {
        Long travelId = 1L;
        Long memberId = 1L;

        travelFacade.changeOnPublicTravel(travelId, memberId);

        verify(travelService).changeOnPublic(travelId, memberId);
    }

    @DisplayName("changeOnPrivateTravel 메서드가 TravelService를 잘 호출하는지")
    @Test
    void changeOnPrivateTravel_ShouldCallService() {
        Long travelId = 1L;
        Long memberId = 1L;

        travelFacade.changeOnPrivateTravel(travelId, memberId);

        verify(travelService).changeOnPrivate(travelId, memberId);
    }
}
