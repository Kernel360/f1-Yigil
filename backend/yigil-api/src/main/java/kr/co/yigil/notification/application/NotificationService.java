package kr.co.yigil.notification.application;

import kr.co.yigil.notification.domain.Notification;
import kr.co.yigil.notification.domain.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final Sinks.Many<Notification> sink = Sinks.many().multicast().onBackpressureBuffer();
    private final NotificationRepository notificationRepository;

    public void sendNotification(Notification notification) {
        notificationRepository.save(notification);
        sendRealTimeNotification(notification);
    }

    private void sendRealTimeNotification(Notification notification) {
        sink.tryEmitNext(notification);
    }

    public Flux<ServerSentEvent<Notification>> getNotificationStream(Long memberId) {
        return sink.asFlux()
                .filter(notification -> notification.getMember().getId().equals(memberId))
                .map(notification -> ServerSentEvent.<Notification>builder()
                        .data(notification)
                        .build());
    }
}
