package kr.co.yigil.notification.infrastructure;

import java.util.Optional;
import kr.co.yigil.notification.domain.Notification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Slice<Notification> findAllByMemberId(Long memberId, Pageable pageable);

    Optional<Notification> findByIdAndMemberId(Long notificationId, Long memberId);
}
