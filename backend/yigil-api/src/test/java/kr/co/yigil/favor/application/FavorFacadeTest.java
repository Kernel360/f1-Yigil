package kr.co.yigil.favor.application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import kr.co.yigil.favor.domain.FavorInfo;
import kr.co.yigil.favor.domain.FavorService;
import kr.co.yigil.notification.domain.NotificationService;
import kr.co.yigil.notification.domain.NotificationType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FavorFacadeTest {

    @Mock
    private FavorService favorService;
    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private FavorFacade favorFacade;

    @DisplayName("좋아요 추가가 잘 되는지")
    @Test
    void WhenAddFavor_ThenShouldReturnAddFavorResponse() {
        Long memberId = 1L;
        Long ownerId = 2L;
        Long travelId = 1L;

        when(favorService.addFavor(memberId, travelId)).thenReturn(ownerId);

        var response = favorFacade.addFavor(memberId, travelId);

        verify(notificationService).sendNotification(NotificationType.FAVOR, memberId, ownerId);
        assertThat(response).isInstanceOf(FavorInfo.AddFavorResponse.class);
    }


    @DisplayName("좋아요 삭제가 잘 되는지")
    @Test
    void WhenDeleteFavor_ThenShouldReturnDeleteFavorResponse() {
        Long memberId = 1L;
        Long travelId = 1L;

        var response = favorFacade.deleteFavor(memberId, travelId);

        verify(favorService).deleteFavor(memberId, travelId);
        assertThat(response).isInstanceOf(FavorInfo.DeleteFavorResponse.class);
    }
}