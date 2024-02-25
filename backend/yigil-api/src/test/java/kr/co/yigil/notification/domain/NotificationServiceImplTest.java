package kr.co.yigil.notification.domain;

import kr.co.yigil.member.Member;
import kr.co.yigil.member.SocialLoginType;
import kr.co.yigil.member.domain.MemberReader;
import kr.co.yigil.notification.domain.util.NotificationCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.test.util.ReflectionTestUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitResult;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class NotificationServiceImplTest {

    @Mock
    private NotificationReader notificationReader;

    @Mock
    private NotificationStore notificationStore;

    @Mock
    private MemberReader memberReader;

    @Mock
    private Sinks.Many<Notification> sink;

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
        Member sender = new Member(senderId,"shin@gmail.com", "123456", "God", "profile.jpg",
                SocialLoginType.KAKAO);
        Member receiver = new Member(receiverId,"shidn@gmail.com", "1d23456", "Godd", "profilde.jpg",
        SocialLoginType.KAKAO);

        Notification notification = new Notification();
        NotificationCreator notificationCreator = (s, r) -> notification;

        when(sink.tryEmitNext(any(Notification.class))).thenReturn(EmitResult.OK);
        ReflectionTestUtils.setField(notificationService, "sink", sink);

        when(memberReader.getMember(senderId)).thenReturn(sender);
        when(memberReader.getMember(receiverId)).thenReturn(receiver);
        doNothing().when(notificationStore).store(any(Notification.class));

        notificationService.sendNotification(notificationCreator, 1L, 2L);

        verify(memberReader, times(2)).getMember(anyLong());
        verify(notificationStore, times(1)).store(any(Notification.class));
        verify(sink, times(1)).tryEmitNext(any(Notification.class));
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