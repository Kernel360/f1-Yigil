package kr.co.yigil.notification.application;

import kr.co.yigil.notification.domain.NotificationInfo;
import kr.co.yigil.notification.domain.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationFacade {
    private final NotificationService notificationService;

    public SseEmitter createEmitter(Long memberId) {
        return notificationService.createEmitter(memberId);
    }

    public NotificationInfo.NotificationsSlice getNotificationSlice(Long memberId, PageRequest pageRequest) {
        return notificationService.getNotificationSlice(memberId, pageRequest);
    }

    public void readNotification(Long memberId, List<Long> ids) {
        notificationService.readNotification(memberId, ids);
    }
}
