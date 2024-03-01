package kr.co.yigil.comment.domain;

import java.util.List;
import lombok.Getter;
import org.springframework.data.domain.Slice;

public class CommentInfo {

    @Getter
    public static class CommentCreateResponse {

        private final String message;
        private final Long notifiedReceiverId;

        public CommentCreateResponse(String message) {
            this.message = message;
            this.notifiedReceiverId = null;
        }
    }


    @Getter
    public static class CommentsResponse {

        private List<CommentsUnitInfo> content;
        private boolean hasNext;

        public CommentsResponse(Slice<Comment> comments) {
            this.content = comments.stream().map(CommentsUnitInfo::new).toList();
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
        private final String createdAt;

        public CommentsUnitInfo(Comment comment) {
            this.id = comment.getId();
            this.content = comment.getContent();
            this.memberId = comment.getMember().getId();
            this.memberNickname = comment.getMember().getNickname();
            this.memberImageUrl = comment.getMember().getProfileImageUrl();
            this.createdAt = comment.getCreatedAt().toString();
        }
    }

    @Getter
    public static class DeleteResponse {

        private final String message;

        public DeleteResponse(String message) {
            this.message = message;
        }
    }

    @Getter
    public static class UpdateResponse {

        private final Long notifiedReceiverId;

        public UpdateResponse(Long notifiedReceiverId) {
            this.notifiedReceiverId = notifiedReceiverId;
        }
    }
}
