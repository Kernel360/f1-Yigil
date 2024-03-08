package kr.co.yigil.comment.infterfaces.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

public class CommentDto {

    @Data
    @AllArgsConstructor
    public static class CommentsResponse{
        Page<CommentUnitDto> comments;
    }

    @Data
    @AllArgsConstructor
    public static class CommentUnitDto{
        private Long id;
        private String memberNickname;
        private Long memberId;
        private String content;
        private LocalDateTime createdAt;
        private List<ReplyUnitDto> children;
    }

    @Data
    @AllArgsConstructor
    public static class ReplyUnitDto {
        private Long id;
        private String memberNickname;
        private Long memberId;
        private String content;
        private LocalDateTime createdAt;
    }

    @Data
    @AllArgsConstructor
    public static class ParentCommentsResponse{
        Page<ParentCommentsInfo> parentComments;
    }

    @Data
    @AllArgsConstructor
    public static class ChildrenCommentsResponse{
        Page<ChildrenCommentsInfo> childrenComments;
    }

    @Data
    @AllArgsConstructor
    public static class ParentCommentsInfo{
        private Long id;
        private String memberNickname;
        private Long memberId;
        private String content;
        private LocalDateTime createdAt;
        private int childrenCount;
    }

    @Data
    @AllArgsConstructor
    public static class ChildrenCommentsInfo{
        private Long id;
        private String memberNickname;
        private Long memberId;
        private String content;
        private LocalDateTime createdAt;
    }

    @Data
    @AllArgsConstructor
    public static class ParentCommentDetailResponse{
        private Long id;
        private String memberNickname;
        private String memberId;
        private String content;
        private LocalDateTime createdAt;
        private int childrenCount;
    }

    @Data
    @AllArgsConstructor
    public static class ChildrenCommentDetailResponse{
        private Long id;
        private String memberNickname;
        private String memberId;
        private String content;
        private LocalDateTime createdAt;
    }


}
