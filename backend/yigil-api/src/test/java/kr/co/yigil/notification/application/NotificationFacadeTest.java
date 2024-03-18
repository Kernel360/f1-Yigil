package kr.co.yigil.notification.application;

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
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.mockito.Mockito.*;

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
    @DisplayName("createEmitter 메서드가 NotificationService의 메서드를 잘 호출하는지")
    void getNotificationStream() {
        Long memberId = 1L;
        SseEmitter mockEmitter = mock(SseEmitter.class);
        when(notificationService.createEmitter(memberId)).thenReturn(mockEmitter);

        notificationFacade.createEmitter(memberId);

        verify(notificationService, times(1)).createEmitter(memberId);
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
        List<Long> notificationIds = List.of(notificationId);

        notificationFacade.readNotification(memberId, notificationIds);

        verify(notificationService, times(1)).readNotification(memberId, notificationIds);
    }
}