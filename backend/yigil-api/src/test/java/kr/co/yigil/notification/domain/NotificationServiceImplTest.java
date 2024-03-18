package kr.co.yigil.notification.domain;

import kr.co.yigil.member.Member;
import kr.co.yigil.member.SocialLoginType;
import kr.co.yigil.member.domain.MemberReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

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

    @DisplayName("유효한 파라미터로 create Emitter 메서드가 잘 호출되는지")
    @Test
    void shouldCallGetNotificationStreamMethodCorrectly_givenValidParameters() {
        Long notifierId = 1L;
        SseEmitter mockEmitter = mock(SseEmitter.class);
        when(notificationSender.createEmitter(notifierId)).thenReturn(mockEmitter);

        notificationService.createEmitter(notifierId);

        verify(notificationSender, times(1)).createEmitter(notifierId);
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
}