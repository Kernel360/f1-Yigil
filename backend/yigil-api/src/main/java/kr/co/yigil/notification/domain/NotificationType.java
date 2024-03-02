package kr.co.yigil.notification.domain;

import java.util.function.BinaryOperator;

public enum NotificationType {
    FOLLOW((sender, receiver) -> sender + "님이 팔로우 하였습니다."),
    UNFOLLOW((sender, receiver) -> sender + "님이 언팔로우 하였습니다."),
    FAVOR((sender, receiver) -> sender + "님이 게시글에 좋아요를 눌렀습니다."),
    NEW_COMMENT((sender, receiver) -> sender + "님이 게시글에 댓글을 달았습니다."),
    REPLY_COMMENT((sender, receiver) -> sender + "님이 댓글을 수정하였습니다.")
    ;

    private final BinaryOperator<String> messageComposer;

    NotificationType(BinaryOperator<String> messageComposer) {
        this.messageComposer = messageComposer;
    }

    public String composeMessage(String sender, String receiver) {
        return messageComposer.apply(sender, receiver);
    }
}
