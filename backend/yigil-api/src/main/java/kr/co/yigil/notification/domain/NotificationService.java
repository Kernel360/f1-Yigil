package kr.co.yigil.notification.domain;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.util.List;

public interface NotificationService {

    SseEmitter createEmitter(Long memberId);

    void sendNotification(NotificationType notificationType, Long senderId, Long receiverId);

    Slice<Notification> getNotificationSlice(Long memberId, PageRequest pageRequest);

    void readNotification(Long memberId, List<Long> notificationId);
}
