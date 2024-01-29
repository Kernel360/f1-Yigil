package kr.co.yigil.notification.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import kr.co.yigil.member.Member;
import kr.co.yigil.member.SocialLoginType;
import kr.co.yigil.notification.domain.Notification;
import kr.co.yigil.notification.domain.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        notificationService = new NotificationService(notificationRepository);
    }

    @Test
    @DisplayName("알림을 보내고 저장하는지 확인")
    void testSendNotification() {
        Member member = new Member(1L, "email", "12345678", "nickname", "image.jpg", SocialLoginType.KAKAO);
        Notification notification = new Notification(member, "새 알림");

        notificationService.sendNotification(notification);

        verify(notificationRepository).save(any(Notification.class));
    }

    @Test
    @DisplayName("특정 회원 ID에 대한 알림 스트림을 올바르게 반환하는지 확인")
    void testGetNotificationStream() {
        Long memberId = 1L;
        Member member = new Member(memberId, "email@example.com", "1234", "nickname", "profile.jpg", SocialLoginType.KAKAO);
        Notification notification = new Notification(member, "새 알림");

        notificationService.sendNotification(notification);

        Flux<ServerSentEvent<Notification>> stream = notificationService.getNotificationStream(memberId);

        StepVerifier.create(stream)
                .expectNextMatches(sse -> sse.data().equals(notification))
                .thenCancel()
                .verify();
    }
}
