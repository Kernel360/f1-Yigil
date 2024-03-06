package kr.co.yigil.notice.domain;

import kr.co.yigil.admin.domain.Admin;
import lombok.Builder;
import lombok.Getter;

public class NoticeCommand {

    @Getter
    @Builder
    public static class NoticeCreateRequest{

        private String title;
        private String content;

        public Notice toEntity(Admin author) {
            return new Notice(author, title, content);
        }

    }

    @Getter
    @Builder
    public static class NoticeUpdateRequest{

        private String title;
        private String content;
    }

}
