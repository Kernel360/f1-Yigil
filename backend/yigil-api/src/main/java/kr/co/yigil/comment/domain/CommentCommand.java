package kr.co.yigil.comment.domain;

import kr.co.yigil.member.Member;
import kr.co.yigil.travel.domain.Travel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class CommentCommand {

    @Getter
    @Builder
    @ToString
    public static class CommentCreateRequest {
        private final String content;
        private final Long parentId;
        private final Long notifiedMemberId;

        public Comment toEntity(Member member, Travel travel, Comment parentComment) {
            return new Comment(
                content,
                member,
                travel,
                parentComment
            );
        }
    }

    @Getter
    @Builder
    @ToString
    public static class CommentUpdateRequest {

        private String content;
        private Long parentId;
        private Long notifiedMemberId;

        public Comment toEntity(Member member, Travel travel, Comment parentComment) {
            return new Comment(
                content,
                member,
                travel,
                parentComment
            );
        }
    }


}
