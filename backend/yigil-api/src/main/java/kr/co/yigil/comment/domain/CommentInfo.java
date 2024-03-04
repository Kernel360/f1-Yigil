package kr.co.yigil.comment.domain;

import java.util.List;
import lombok.Getter;
import org.springframework.data.domain.Slice;

public class CommentInfo {

    @Getter
    public static class CommentNotiInfo {

        private final Long parentCommentMemberId;
        private final Long travelMemberId;

        public CommentNotiInfo(Comment comment) {
            this.parentCommentMemberId =
                comment.getParent() != null ? comment.getParent().getMember().getId() : null;
            this.travelMemberId = comment.getTravel().getMember().getId();
        }

        public Long getNotificationMemberId(Long memberId) {
            if (parentCommentMemberId != null && !parentCommentMemberId.equals(memberId)) {
                return parentCommentMemberId;
            }
            assert travelMemberId != null;
            if (!travelMemberId.equals(memberId)) {
                return travelMemberId;
            }
            return null;
        }
    }


    @Getter
    public static class CommentsResponse {

        private final List<CommentsUnitInfo> content;
        private final boolean hasNext;

        public CommentsResponse(List<CommentsUnitInfo> comments, boolean hasNext) {
            this.content = comments;
            this.hasNext = hasNext;
        }

        public CommentsResponse(Slice<Comment> comments) {
            this.content = comments.getContent().stream().map(CommentsUnitInfo::new).toList();
            this.hasNext = comments.hasNext();
        }
    }

    @Getter
    public static class CommentsUnitInfo {

        private final Long id;
        private final String content;
        private final Long memberId;
        private final String memberNickname;
        private final String memberImageUrl;
        private final int childCount;
        private final String createdAt;

        public CommentsUnitInfo(Comment comment, int childCount) {
            this.id = comment.getId();
            this.content = comment.getContent();
            this.memberId = comment.getMember().getId();
            this.memberNickname = comment.getMember().getNickname();
            this.memberImageUrl = comment.getMember().getProfileImageUrl();
            this.childCount = childCount;
            this.createdAt = comment.getCreatedAt().toString();
        }

        public CommentsUnitInfo(Comment comment) {
            this.id = comment.getId();
            this.content = comment.getContent();
            this.memberId = comment.getMember().getId();
            this.memberNickname = comment.getMember().getNickname();
            this.memberImageUrl = comment.getMember().getProfileImageUrl();
            this.childCount = 0;
            this.createdAt = comment.getCreatedAt().toString();
        }
    }

    public record DeleteResponse(String message) {

    }
}
