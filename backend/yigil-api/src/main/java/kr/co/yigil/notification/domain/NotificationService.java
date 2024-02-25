package kr.co.yigil.notification.domain;

import kr.co.yigil.notification.domain.util.NotificationCreator;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;

public interface NotificationService {

    Flux<ServerSentEvent<Notification>> getNotificationStream(Long memberId);

    void sendNotification(NotificationCreator notificationCreator, Long senderId, Long receiverId);

    Slice<Notification> getNotificationSlice(Long memberId, PageRequest pageRequest);
}
