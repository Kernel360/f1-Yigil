package kr.co.yigil.notification.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.notification.domain.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

public class NotificationReaderImplTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationReaderImpl notificationReader;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("getNotificationSlice 메서드가 올바른 Slice를 반환하는지")
    @Test
    void whenGetNotificationSlice_thenReturnsCorrectSlice() {
        Long memberId = 1L;
        List<Notification> notifications = new ArrayList<>();
        Slice<Notification> expectedSlice = new SliceImpl<>(notifications);

        when(notificationRepository.findAllByReceiverIdAndReadIsFalse(memberId, Pageable.unpaged())).thenReturn(
                expectedSlice);

        Slice<Notification> actualSlice = notificationReader.getNotificationSlice(memberId,
                Pageable.unpaged());

        assertEquals(expectedSlice, actualSlice);
    }

    @DisplayName("getNotification 메서드가 올바른 Notification을 반환하는지")
    @Test
    void whenGetNotification_thenReturnsCorrectNotification() {
        Long memberId = 1L;
        Long notificationId = 1L;
        Notification mockNotification = mock(Notification.class);

        when(notificationRepository.findByIdAndReceiverId(anyLong(), anyLong())).thenReturn(
            java.util.Optional.of(mockNotification));

        var result = notificationReader.getNotification(memberId, notificationId);

        assertThat(result).isEqualTo(mockNotification);
    }

    @DisplayName("해당 notification이 없으면 BadRequestException을 던지는지")
    @Test
    void GivenInvalidNotificationId_whenGetNotification_thenReturnsCorrectNotification() {
        Long memberId = 1L;
        Long notificationId = 1L;

        when(notificationRepository.findByIdAndReceiverId(anyLong(), anyLong())).thenReturn(
            java.util.Optional.empty());

        assertThatThrownBy(() -> notificationReader.getNotification(memberId, notificationId))
            .isInstanceOf(BadRequestException.class);
    }

}