package kr.co.yigil.notice.domain;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import org.springframework.data.domain.Page;

public class NoticeInfo {

    public abstract static class BaseResponse {

        private final String message;

        protected BaseResponse(String message) {
            this.message = message;
        }
    }

    @Getter
    public static class NoticeListInfo {

        private final List<NoticeItem> noticeList;
        private final boolean hasNext;

        public NoticeListInfo(Page<Notice> noticeSlice) {
            this.noticeList = noticeSlice.getContent().stream().map(NoticeItem::new).toList();
            this.hasNext = noticeSlice.hasNext();
        }
    }

    @Getter
    public static class NoticeItem {

        private final Long noticeId;
        private final String title;
        private final Long authorId;
        private final String author;
        private final LocalDateTime createdAt;

        public NoticeItem(Notice notice) {
            noticeId = notice.getId();
            title = notice.getTitle();
            author = notice.getAuthor().getNickname();
            authorId = notice.getAuthor().getId();
            createdAt = notice.getCreatedAt();
        }
    }

    @Getter
    public static class NoticeCreateResponse extends BaseResponse {

        public NoticeCreateResponse(String message) {
            super(message);
        }
    }

    @Getter
    public static class NoticeUpdateResponse extends BaseResponse {

        public NoticeUpdateResponse(String message) {
            super(message);
        }
    }

    @Getter
    public static class NoticeDeleteResponse extends BaseResponse {

        public NoticeDeleteResponse(String message) {
            super(message);
        }
    }

    @Getter
    public static class NoticeDetail {

        private final Long noticeId;
        private final String title;
        private final String content;
        private final Long authorId;
        private final String authorNickname;
        private final String profileImageUrl;
        private final LocalDateTime createdAt;

        public NoticeDetail(Notice notice) {
            this.noticeId = notice.getId();
            this.title = notice.getTitle();
            this.content = notice.getContent();
            this.authorNickname = notice.getAuthor().getNickname();
            this.authorId = notice.getAuthor().getId();
            this.profileImageUrl = notice.getAuthor().getProfileImageUrl();
            this.createdAt = notice.getCreatedAt();
        }
    }
}
