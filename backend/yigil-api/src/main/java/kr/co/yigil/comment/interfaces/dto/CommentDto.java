package kr.co.yigil.comment.interfaces.dto;

import lombok.*;

import java.util.List;

public class CommentDto {

    //request

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CommentCreateRequest {

        private String content;
        private Long parentId;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CommentUpdateRequest {

        private String content;
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

        private boolean deleted;
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
    @ToString
    @AllArgsConstructor
    public static class CommentDeleteResponse {

        private final String message;
    }


}
