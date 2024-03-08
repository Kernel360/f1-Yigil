package kr.co.yigil.comment.infterfaces.controller;

import kr.co.yigil.comment.application.CommentFacade;
import kr.co.yigil.comment.domain.CommentInfo;
import kr.co.yigil.comment.domain.CommentInfo.CommentList;
import kr.co.yigil.comment.infterfaces.dto.CommentDto.ChildrenCommentsResponse;
import kr.co.yigil.comment.infterfaces.dto.CommentDto.CommentsResponse;
import kr.co.yigil.comment.infterfaces.dto.CommentDto.ParentCommentsResponse;
import kr.co.yigil.comment.infterfaces.dto.mapper.CommentMapper;
import kr.co.yigil.global.SortBy;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentApiController {

    private final CommentFacade commentFacade;
    private final CommentMapper commentMapper;

    @GetMapping("/{travel_id}")

    public ResponseEntity<CommentsResponse> getCommentList(
        @PathVariable("travel_id") Long travelId,
        @PageableDefault(size = 5, page = 1) Pageable pageable
    ) {
        var pageRequest = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize()
            , Sort.by(Direction.DESC, SortBy.CREATED_AT.getValue()));
        CommentList info = commentFacade.getComments(travelId,
            pageRequest);
        CommentsResponse response = commentMapper.of(info);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{travel_id}/parents")
    public ResponseEntity<ParentCommentsResponse> getParentCommentList(
        @PathVariable("travel_id") Long travelId,
        @PageableDefault(size = 5, page = 1) Pageable pageable
    ) {
        var pageRequest = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize()
            , Sort.by(Direction.DESC, SortBy.CREATED_AT.getValue()));
        CommentInfo.ParentPageComments info = commentFacade.getParentComments(travelId,
            pageRequest);
        ParentCommentsResponse response = commentMapper.of(info);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{parent_id}/children")
    public ResponseEntity<ChildrenCommentsResponse> getChildrenCommentList(
        @PathVariable("parent_id") Long parentId,
        @PageableDefault(size = 5, page = 1) Pageable pageable
    ) {
        var pageRequest = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize()
            , Sort.by(Direction.DESC, SortBy.CREATED_AT.getValue()));
        CommentInfo.ChildrenPageComments info = commentFacade.getChildrenComments(parentId,
            pageRequest);
        ChildrenCommentsResponse response = commentMapper.of(info);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{comment_id}")
    public ResponseEntity<String> deleteComment(@PathVariable("comment_id") Long commentId) {
        commentFacade.deleteComment(commentId);
        return ResponseEntity.ok().body("댓글 삭제 성공");
    }

}
