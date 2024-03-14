package kr.co.yigil.notification.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import kr.co.yigil.member.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import reactor.core.publisher.Flux;

class NotificationServiceImplTest {

    @Mock
    private NotificationReader notificationReader;

    @Mock
    private NotificationSender notificationSender;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("유효한 파라미터로 getNotificationStream 메서드가 잘 호출되는지")
    @Test
    void shouldCallGetNotificationStreamMethodCorrectly_givenValidParameters() {
        Long notifierId = 1L;
        when(notificationReader.getNotificationStream(anyLong())).thenReturn(Flux.empty());

        notificationService.getNotificationStream(notifierId);

        verify(notificationReader, times(1)).getNotificationStream(notifierId);
    }


    @DisplayName("유효한 파라미터로 sendNotification 메서드가 잘 호출되는지")
    @Test
    void shouldCallSendNotificationMethodCorrectly_givenValidParameters() {
        Long senderId = 1L;
        Long receiverId = 2L;
        NotificationType notificationType = NotificationType.FOLLOW;

        notificationService.sendNotification(notificationType, senderId, receiverId);

        verify(notificationSender).sendNotification(notificationType, senderId, receiverId);
    }

    @DisplayName("When valid parameters, getNotificationSlice method should be called correctly")
    @Test
    void shouldCallGetNotificationSliceMethodCorrectly_givenValidParameters() {
        Slice<Notification> slice = mock(Slice.class);
        when(notificationReader.getNotificationSlice(anyLong(), any(PageRequest.class))).thenReturn(
                slice);

        notificationService.getNotificationSlice(1L, PageRequest.of(0, 10));

        verify(notificationReader, times(1)).getNotificationSlice(anyLong(),
                any(PageRequest.class));
    }

    @DisplayName("When valid parameters, readNotification method should be called correctly")
    @Test
    void givenValidParameters_whenReadNotification_thenShouldBeCalledCorrectly() {
        Notification notification = new Notification(mock(Member.class), "message");
        when(notificationReader.getNotification(anyLong(), anyLong())).thenReturn(notification);

        notificationService.readNotification(1L, 1L);

        verify(notificationReader, times(1)).getNotification(1L, 1L);
        assertThat(notification.isRead()).isTrue();
    }

}