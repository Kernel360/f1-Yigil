package kr.co.yigil.notification.domain.util;

import kr.co.yigil.member.Member;
import kr.co.yigil.member.domain.MemberReader;
import kr.co.yigil.notification.domain.Notification;
import lombok.RequiredArgsConstructor;

public interface NotificationCreator {

    Notification createNotification(Member sender, Member receiver);
}
