package kr.co.yigil.notification.domain;

import java.util.function.BinaryOperator;

public enum NotificationType {
    SPOT_DELETED((sender, receiever)-> sender + "님이 게시글 리뷰를 삭제하셨습니다"),
    COURSE_DELETED((sender, receiever)-> sender + "님이 일정을 삭제하셨습니다");

    private final BinaryOperator<String> messageComposer;

    NotificationType(BinaryOperator<String> messageComposer) {
        this.messageComposer = messageComposer;
    }

    public String composeMessage(String sender, String receiver) {
        return messageComposer.apply(sender, receiver);
    }
}
