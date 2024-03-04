package kr.co.yigil.notice.interfaces.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class NoticeDto {

    @Getter
    @Builder
    public static class NoticeCreateRequest{
        private String author;
        private String title;
        private String content;

    }

    @Getter
    @Builder
    public static class NoticeUpdateRequest{
        private String author;
        private String title;
        private String content;
    }

    @Getter
    @AllArgsConstructor
    public abstract static class BaseMessageResponse {
        private final String message;
    }


    @Getter
    @Builder
    public static class NoticeListResponse{
        List<NoticeItem> noticeList;
        boolean hasNext;
    }
    public static class NoticeItem {
        private Long id;
        private String title;
        private String content;
        private String writer;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
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
        private Long id;
        private String title;
        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }



}
