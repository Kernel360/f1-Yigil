package kr.co.yigil.notification.domain;

import java.util.function.BiFunction;

public enum NotificationType {

    FOLLOW((sender, receiver) -> sender + "님이 팔로우 하였습니다."),
    UNFOLLOW((sender, receiver) -> sender + "님이 언팔로우 하였습니다.");

    private final BiFunction<String, String, String> messageComposer;

    NotificationType(BiFunction<String, String, String> messageComposer) {
        this.messageComposer = messageComposer;
    }

    public String composeMessage(String sender, String receiver) {
        return messageComposer.apply(sender, receiver);
    }
}
