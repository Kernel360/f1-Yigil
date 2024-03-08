package kr.co.yigil.comment.domain;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class CommentInfo {


    @Data
    @AllArgsConstructor
    public static class CommentList {

        private Page<CommentListUnit> parents;

        public CommentList(List<CommentListUnit> parents, Pageable pageable, long total) {
            this.parents = new PageImpl<>(parents, pageable, total);
        }
    }

    @Data
    @AllArgsConstructor
    public static class CommentListUnit {
        private Long id;
        private String memberNickname;
        private Long memberId;
        private String content;
        private LocalDateTime createdAt;
        private List<ReplyListUnit> children;

        public CommentListUnit(Comment comment, List<ReplyListUnit> children) {
            this.id = comment.getId();
            this.memberNickname = comment.getMember().getNickname();
            this.memberId = comment.getMember().getId();
            this.content = comment.getContent();
            this.createdAt = comment.getCreatedAt();
            this.children = children;
        }
    }
    @Data
    @AllArgsConstructor
    public static class ReplyListUnit {
        private Long id;
        private String memberNickname;
        private Long memberId;
        private String content;
        private LocalDateTime createdAt;

        public ReplyListUnit(Comment comment) {
            this.id = comment.getId();
            this.memberNickname = comment.getMember().getNickname();
            this.memberId = comment.getMember().getId();
            this.content = comment.getContent();
            this.createdAt = comment.getCreatedAt();
        }
    }


    @Data
    @AllArgsConstructor
    public static class ParentPageComments {

        Page<ParentListInfo> parentComments;

        public ParentPageComments(List<ParentListInfo> parentComments, Pageable pageable,
            long total) {
            this.parentComments = new PageImpl<>(parentComments, pageable, total);
        }
    }

    @Data
    public static class ChildrenPageComments {

        Page<ChildrenListInfo> childrenComments;

        public ChildrenPageComments(List<ChildrenListInfo> childrenComments, Pageable pageable,
            long total) {

            this.childrenComments = new PageImpl<>(childrenComments, pageable, total);

        }
    }

    @Data
    @AllArgsConstructor
    public static class ParentListInfo {

        private Long id;
        private String memberNickname;
        private Long memberId;
        private String content;
        private LocalDateTime createdAt;
        private int childrenCount;

        public ParentListInfo(Comment comment, int childrenCount) {
            this.id = comment.getId();
            this.memberNickname = comment.getMember().getNickname();
            this.memberId = comment.getMember().getId();
            this.content = comment.getContent();
            this.createdAt = comment.getCreatedAt();
            this.childrenCount = childrenCount;
        }
    }

    @Data
    @AllArgsConstructor
    public static class ChildrenListInfo {

        private Long id;
        private String memberNickname;
        private Long memberId;
        private String content;
        private LocalDateTime createdAt;

        public ChildrenListInfo(Comment comment) {
            this.id = comment.getId();
            this.memberNickname = comment.getMember().getNickname();
            this.memberId = comment.getMember().getId();
            this.content = comment.getContent();
            this.createdAt = comment.getCreatedAt();
        }
    }

    @Data
    @AllArgsConstructor
    public static class ParentDetailResponse {

        private Long id;
        private String memberNickname;
        private String memberId;
        private String content;
        private LocalDateTime createdAt;
        private int childrenCount;
    }

    @Data
    @AllArgsConstructor
    public static class ChildrenDetailResponse {

        private Long id;
        private String memberNickname;
        private String memberId;
        private String content;
        private LocalDateTime createdAt;
    }
}