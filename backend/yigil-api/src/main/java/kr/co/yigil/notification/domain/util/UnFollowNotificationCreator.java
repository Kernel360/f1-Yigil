package kr.co.yigil.notification.domain.util;

import kr.co.yigil.member.Member;
import kr.co.yigil.notification.domain.Notification;
import org.springframework.stereotype.Component;

@Component
public class UnFollowNotificationCreator implements NotificationCreator{

    @Override
    public Notification createNotification(Member unfollower, Member unfollowing) {
        String message = unfollower.getNickname() + "님이 언팔로우 하였습니다.";
        return new Notification(unfollowing, message);    }
}
