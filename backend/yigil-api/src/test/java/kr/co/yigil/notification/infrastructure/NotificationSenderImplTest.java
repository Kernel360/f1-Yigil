package kr.co.yigil.notification.infrastructure;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import kr.co.yigil.member.Member;
import kr.co.yigil.member.SocialLoginType;
import kr.co.yigil.member.domain.MemberReader;
import kr.co.yigil.notification.domain.Notification;
import kr.co.yigil.notification.domain.NotificationFactory;
import kr.co.yigil.notification.domain.NotificationSender;
import kr.co.yigil.notification.domain.NotificationStore;
import kr.co.yigil.notification.domain.NotificationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitResult;
import reactor.test.StepVerifier;

class NotificationSenderImplTest {

    @Mock
    private MemberReader memberReader;

    @Mock
    private NotificationStore notificationStore;

    @Mock
    private NotificationFactory notificationFactory;

    @Mock
    private Sinks.Many<Notification> sink = Sinks.many().multicast().onBackpressureBuffer();

    @InjectMocks
    private NotificationSenderImpl notificationSender;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @DisplayName("유효한 파라미터로 sendNotification 메서드가 잘 호출되는지")
    @Test
    void shouldCallSendNotificationMethodCorrectly_givenValidParameters() {
        Long senderId = 1L;
        Long receiverId = 2L;
        NotificationType notificationType = NotificationType.FOLLOW;

        Member sender = new Member(senderId,"shin@gmail.com", "123456", "sendernickname", "profile.jpg",
                SocialLoginType.KAKAO);
        Member receiver = new Member(receiverId,"shidn@gmail.com", "1d23456", "receivernickname", "profilde.jpg",
                SocialLoginType.KAKAO);
        Notification notification = new Notification(receiver, "message");

        when(memberReader.getMember(senderId)).thenReturn(sender);
        when(memberReader.getMember(receiverId)).thenReturn(receiver);
        when(notificationFactory.createNotification(notificationType, sender, receiver)).thenReturn(notification);
        when(sink.tryEmitNext(any(Notification.class))).thenReturn(EmitResult.OK);
        ReflectionTestUtils.setField(notificationSender, "sink", sink);

        notificationSender.sendNotification(notificationType, senderId, receiverId);

        verify(memberReader, times(2)).getMember(anyLong());
        verify(notificationFactory).createNotification(any(NotificationType.class), any(Member.class), any(Member.class));
        verify(notificationStore).store(any(Notification.class));
        verify(sink, times(1)).tryEmitNext(any(Notification.class));
    }
}