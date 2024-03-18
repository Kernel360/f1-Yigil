package kr.co.yigil.notification.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;

public interface NotificationReader {

    Flux<ServerSentEvent<Notification>> getNotificationStream(Long memberId);

    Slice<Notification> getNotificationSlice(Long memberId, Pageable pageable);

    Notification getNotification(Long memberId, Long notificationId);
}
