package kr.co.yigil.notification.domain.util;

import kr.co.yigil.member.Member;
import kr.co.yigil.notification.domain.Notification;
import org.geolatte.geom.M;
import org.springframework.stereotype.Component;

@Component
public class FollowNotificationCreator implements NotificationCreator{

    @Override
    public Notification createNotification(Member follower, Member following) {
        String message = follower.getNickname() + "님이 팔로우 하였습니다.";
        return new Notification(following, message);    }
}
