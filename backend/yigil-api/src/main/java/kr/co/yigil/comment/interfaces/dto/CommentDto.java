package kr.co.yigil.comment.interfaces.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class CommentDto {

    //request

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CommentCreateRequest {

        private String content;
        private Long parentId;
        private Long notifiedMemberId;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CommentUpdateRequest {

        private String content;
        private Long parentId;
        private Long notifiedMemberId;
    }

    //response


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CommentCreateResponse {

        private String message;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CommentsResponse {

        private List<CommentsUnitInfo> content;
        private boolean hasNext;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CommentsUnitInfo{

        private Long id;
        private String content;
        private Long memberId;
        private String memberNickname;
        private String memberImageUrl;
        private int childCount;
        private String createdAt;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CommentUpdateResponse {
        private String message;
    }

    @Getter
    @Builder
    @ToString
    public static class CommentDeleteResponse {

        private final String message;
    }


}
