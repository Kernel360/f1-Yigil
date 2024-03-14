package kr.co.yigil.notification.application;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import kr.co.yigil.notification.domain.Notification;
import kr.co.yigil.notification.domain.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;

class NotificationFacadeTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationFacade notificationFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("getNotificationStream 메서드가 NotificationService의 메서드를 잘 호출하는지")
    void getNotificationStream() {
        Long memberId = 1L;
        Flux<ServerSentEvent<Notification>> notificationFlux = Flux.empty();
        when(notificationService.getNotificationStream(memberId)).thenReturn(notificationFlux);

        notificationFacade.getNotificationStream(memberId);

        verify(notificationService, times(1)).getNotificationStream(memberId);
    }

    @Test
    @DisplayName("getNotificationSlice 메서드가 NotificationService의 메서드를 잘 호출하는지")
    void getNotificationSlice() {
        Long memberId = 1L;
        PageRequest pageRequest = PageRequest.of(0, 10);
        Slice<Notification> notificationSlice = mock(Slice.class);
        when(notificationService.getNotificationSlice(memberId, pageRequest)).thenReturn(notificationSlice);

        notificationFacade.getNotificationSlice(memberId, pageRequest);

        verify(notificationService, times(1)).getNotificationSlice(memberId, pageRequest);
    }

    @DisplayName("readNotification 메서드가 NotificationService의 메서드를 잘 호출하는지")
    @Test
    void readNotification() {
        Long memberId = 1L;
        Long notificationId = 1L;

        notificationFacade.readNotification(memberId, notificationId);

        verify(notificationService, times(1)).readNotification(memberId, notificationId);
    }
}