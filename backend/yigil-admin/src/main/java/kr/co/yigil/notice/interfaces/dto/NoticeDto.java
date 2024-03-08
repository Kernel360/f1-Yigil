package kr.co.yigil.notice.interfaces.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

public class NoticeDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NoticeCreateRequest{
        private String title;
        private String content;
    }

    @Getter
    @AllArgsConstructor
    public static class NoticeUpdateRequest{
        private String title;
        private String content;
    }

    @Getter
    @AllArgsConstructor
    public abstract static class BaseMessageResponse {
        private final String message;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NoticeListResponse{
        Page<NoticeItem> noticeList;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NoticeItem {
        private Long noticeId;
        private String title;
        private String authorId;
        private String author;
        private LocalDateTime createdAt;
    }

    @Getter
    public static class NoticeCreateResponse extends BaseMessageResponse {
        public NoticeCreateResponse(String message) {
            super(message);
        }
    }

    @Getter
    public static class NoticeUpdateResponse extends BaseMessageResponse {
        public NoticeUpdateResponse(String message) {
            super(message);
        }
    }

    @Getter
    public static class NoticeDeleteResponse extends BaseMessageResponse {
        public NoticeDeleteResponse(String message) {
            super(message);
        }
    }

    @Getter
    @Builder
    public static class NoticeDetailResponse {
        private Long noticeId;
        private String title;
        private String content;
        private Long authorId;
        private String authorNickname;
        private String profileImageUrl;
        private LocalDateTime createdAt;
    }
}
