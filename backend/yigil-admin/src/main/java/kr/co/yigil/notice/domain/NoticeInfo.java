package kr.co.yigil.notice.domain;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import org.springframework.data.domain.Slice;

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

        public NoticeListInfo(Slice<Notice> noticeSlice) {
            this.noticeList = noticeSlice.getContent().stream().map(NoticeItem::new).toList();
            this.hasNext = noticeSlice.hasNext();
        }
    }

    @Getter
    public static class NoticeItem {

        private final Long id;
        private final String title;
        private final String content;
        private final String writer;
        private final LocalDateTime createdAt;
        private final LocalDateTime updatedAt;

        public NoticeItem(Notice notice) {
            id = notice.getId();
            title = notice.getTitle();
            content = notice.getContent();
            writer = notice.getAuthor();
            createdAt = notice.getCreatedAt();
            updatedAt = notice.getModifiedAt();
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

        private final Long id;
        private final String title;
        private final String content;
        private final LocalDateTime createdAt;
        private final LocalDateTime updatedAt;

        public NoticeDetail(Notice notice) {
            this.id = notice.getId();
            this.title = notice.getTitle();
            this.content = notice.getContent();
            this.createdAt = notice.getCreatedAt();
            this.updatedAt = notice.getModifiedAt();
        }
    }
}
