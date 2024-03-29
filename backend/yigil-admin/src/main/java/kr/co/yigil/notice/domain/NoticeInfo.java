package kr.co.yigil.notice.domain;

import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class NoticeInfo {

    public abstract static class BaseResponse {

        private final String message;

        protected BaseResponse(String message) {
            this.message = message;
        }
    }

    @Getter
    public static class NoticeListInfo {

        private final PageImpl<NoticeItem> noticeList;

        public NoticeListInfo(Page<Notice> noticePage) {
            this.noticeList = new PageImpl<>(
                noticePage.getContent().stream().map(NoticeItem::new).toList(),
                noticePage.getPageable(),
                noticePage.getTotalElements());
        }
    }

    @Getter
    public static class NoticeItem {

        private final Long noticeId;
        private final String title;
        private final Long authorId;
        private final String author;
        private final String authorEmail;
        private final String authorProfileImageUrl;
        private final LocalDateTime createdAt;

        public NoticeItem(Notice notice) {
            noticeId = notice.getId();
            title = notice.getTitle();
            author = notice.getAuthor().getNickname();
            authorId = notice.getAuthor().getId();
            authorEmail = notice.getAuthor().getEmail();
            authorProfileImageUrl = notice.getAuthorProfileImage();
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
            this.profileImageUrl = notice.getAuthorProfileImage();
            this.createdAt = notice.getCreatedAt();
        }
    }
}
