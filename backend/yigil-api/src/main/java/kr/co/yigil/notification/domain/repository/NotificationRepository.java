package kr.co.yigil.notification.domain.repository;

import kr.co.yigil.notification.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
