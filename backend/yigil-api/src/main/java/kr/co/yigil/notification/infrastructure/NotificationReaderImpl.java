package kr.co.yigil.notification.infrastructure;

import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.notification.domain.Notification;
import kr.co.yigil.notification.domain.NotificationReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationReaderImpl implements NotificationReader {

    private final NotificationRepository notificationRepository;

    @Override
    public Slice<Notification> getNotificationSlice(Long memberId, Pageable pageable) {
        return notificationRepository.findAllByReceiverIdAndReadIsFalse(memberId, pageable);
    }

    @Override
    public Notification getNotification(Long memberId, Long notificationId) {
        return notificationRepository.findByIdAndReceiverId(notificationId, memberId)
            .orElseThrow(() -> new BadRequestException(ExceptionCode.INVALID_REQUEST));
    }
}
