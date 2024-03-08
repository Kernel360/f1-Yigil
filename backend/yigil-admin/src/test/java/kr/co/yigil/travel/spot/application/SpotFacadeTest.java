package kr.co.yigil.travel.spot.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import kr.co.yigil.admin.domain.admin.AdminService;
import kr.co.yigil.notification.domain.NotificationService;
import kr.co.yigil.notification.domain.NotificationType;
import kr.co.yigil.travel.spot.domain.SpotInfoDto;
import kr.co.yigil.travel.spot.domain.SpotService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class SpotFacadeTest {

    @Mock
    private SpotService spotService;
    @Mock
    private NotificationService notificationService;
    @Mock
    private AdminService adminService;

    @InjectMocks
    private SpotFacade spotFacade;

    @DisplayName("getSpots 메서드가 SpotService를 잘 호출하는지")
    @Test
    void whenGetSpots_thenShouldNotthrowAnError() {

        when(spotService.getSpots(any(Pageable.class))).thenReturn(
            mock(SpotInfoDto.SpotPageInfo.class));

        var response = spotFacade.getSpots(mock(Pageable.class));
        assertThat(response).isInstanceOf(SpotInfoDto.SpotPageInfo.class);

    }

    @DisplayName("getSpot 메서드가 SpotService를 잘 호출하는지")
    @Test
    void whenGetSpot_thenShouldReturnSpotDetailInfo() {
        when(spotService.getSpot(any(Long.class))).thenReturn(
            mock(SpotInfoDto.SpotDetailInfo.class));
        var response = spotFacade.getSpot(1L);
        assertThat(response).isInstanceOf(SpotInfoDto.SpotDetailInfo.class);
    }

    @DisplayName("deleteSpot 메서드가 SpotService를 잘 호출하는지")
    @Test
    void whenDeleteSpot_thenShouldNotThrowAnError() {
        Long memberId = 1L;
        Long adminId = 3L;
        when(spotService.deleteSpot(any(Long.class))).thenReturn(memberId);
        when(adminService.getAdminId()).thenReturn(adminId);
        spotFacade.deleteSpot(1L);

        verify(notificationService).sendNotification(NotificationType.SPOT_DELETED, adminId, memberId);

    }
}