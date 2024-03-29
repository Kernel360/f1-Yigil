package kr.co.yigil.notification.infrastructure;

import kr.co.yigil.notification.domain.Notification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Slice<Notification> findAllByReceiverIdAndReadIsFalse(Long memberId, Pageable pageable);

    Optional<Notification> findByIdAndReceiverId(Long notificationId, Long memberId);
}
