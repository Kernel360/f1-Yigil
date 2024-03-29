package kr.co.yigil.notification.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{
    private final NotificationReader notificationReader;
    private final NotificationSender notificationSender;


    @Override
    public SseEmitter createEmitter(Long memberId) {
        return notificationSender.createEmitter(memberId);
    }

    @Transactional
    @Override
    public void sendNotification(NotificationType notificationType, Long senderId, Long receiverId) {
        notificationSender.sendNotification(notificationType, senderId, receiverId);
    }

    @Transactional(readOnly = true)
    @Override
    public NotificationInfo.NotificationsSlice getNotificationSlice(Long memberId, PageRequest pageRequest) {
        var notifications = notificationReader.getNotificationSlice(memberId, pageRequest);
        return new NotificationInfo.NotificationsSlice(notifications);

    }

    @Transactional
    @Override
    public void readNotification(Long memberId, List<Long> ids) {
        for(Long notificationId: ids) {
            Notification notification = notificationReader.getNotification(memberId, notificationId);
            notification.read();
        }
    }
}
