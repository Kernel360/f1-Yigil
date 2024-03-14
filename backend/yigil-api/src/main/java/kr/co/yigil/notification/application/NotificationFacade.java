package kr.co.yigil.notification.application;

import kr.co.yigil.notification.domain.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import kr.co.yigil.notification.domain.NotificationService;

@Service
@RequiredArgsConstructor
public class NotificationFacade {
    private final NotificationService notificationService;

    public Flux<ServerSentEvent<Notification>> getNotificationStream(Long memberId) {
        return notificationService.getNotificationStream(memberId);
    }

    public Slice<Notification> getNotificationSlice(Long memberId, PageRequest pageRequest) {
        return notificationService.getNotificationSlice(memberId, pageRequest);
    }

    public void readNotification(Long memberId, Long notificationId) {
        notificationService.readNotification(memberId, notificationId);
    }
}
