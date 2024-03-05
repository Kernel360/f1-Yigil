package kr.co.yigil.notice.domain;

import lombok.Builder;
import lombok.Getter;

public class NoticeCommand {

    @Getter
    @Builder
    public static class NoticeCreateRequest{
        private String author;
        private String title;
        private String content;

        public Notice toEntity() {
            return new Notice(author, title, content);
        }
    }

    @Getter
    @Builder
    public static class NoticeUpdateRequest{
        private String author;
        private String title;
        private String content;

        public Notice toEntity() {
            return new Notice(author, title, content);
        }
    }

}
