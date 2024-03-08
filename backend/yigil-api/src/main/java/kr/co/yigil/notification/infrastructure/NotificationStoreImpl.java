package kr.co.yigil.notification.infrastructure;

import kr.co.yigil.notification.domain.Notification;
import kr.co.yigil.notification.domain.NotificationStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationStoreImpl implements NotificationStore {
    private final NotificationRepository notificationRepository;

    @Override
    public void store(Notification notification) {
        notificationRepository.save(notification);
    }

}
