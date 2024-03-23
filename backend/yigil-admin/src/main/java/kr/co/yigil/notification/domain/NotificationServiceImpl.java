package kr.co.yigil.notification.domain;

import kr.co.yigil.admin.domain.Admin;
import kr.co.yigil.admin.domain.admin.AdminReader;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.domain.MemberReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final MemberReader memberReader;
    private final AdminReader adminReader;
    private final NotificationStore notificationStore;
    private final NotificationFactory notificationFactory;

    @Override
    @Transactional
    public void saveNotification(NotificationType notificationType, Long adminId, Long receiverId) {
        Admin sender = adminReader.getAdmin(adminId);
        Member receiver = memberReader.getMember(receiverId);
        Notification notification = notificationFactory.createNotification(notificationType, sender, receiver);
        notificationStore.store(notification);
    }
}
