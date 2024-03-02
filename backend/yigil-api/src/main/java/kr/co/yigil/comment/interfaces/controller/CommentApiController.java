package kr.co.yigil.comment.interfaces.controller;

import kr.co.yigil.auth.Auth;
import kr.co.yigil.auth.MemberOnly;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.comment.application.CommentFacade;
import kr.co.yigil.comment.domain.CommentInfo;
import kr.co.yigil.comment.interfaces.dto.CommentDto;
import kr.co.yigil.comment.interfaces.dto.CommentDto.CommentCreateRequest;
import kr.co.yigil.comment.interfaces.dto.CommentDto.CommentCreateResponse;
import kr.co.yigil.comment.interfaces.dto.mapper.CommentMapper;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentFacade commentFacade;
    private final CommentMapper commentMapper;

    @PostMapping("/travels/{travel_id}")
    @MemberOnly
    public ResponseEntity<CommentDto.CommentCreateResponse> createComment(
        @RequestBody CommentCreateRequest commentCreateRequest,
        @Auth final Accessor accessor,
        @PathVariable("travel_id") Long travelId
    ) {

        var command = commentMapper.of(commentCreateRequest);
        commentFacade.createComment(accessor.getMemberId(), travelId, command);
        return ResponseEntity.ok().body(new CommentCreateResponse("댓글 생성 성공"));
    }

    @GetMapping("/travels/{travel_id}")
    public ResponseEntity<CommentDto.CommentsResponse> getParentCommentList(
        @PathVariable("travel_id") Long travelId,
        @PageableDefault(size = 5, page = 1 ) Pageable pageable
    ) {

        var pageRequest = PageRequest.of(pageable.getPageNumber()-1, pageable.getPageSize()
            , Sort.by(Sort.Direction.ASC, "createdAt"));
        CommentInfo.CommentsResponse parentCommentList = commentFacade.getParentCommentList(
            travelId,
            pageRequest);
        var response = commentMapper.of(parentCommentList);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/parents/{comment_id}")
    public ResponseEntity<CommentDto.CommentsResponse> getChildCommentList(
        @PathVariable("comment_id") Long commentId,
        @PageableDefault(size = 5, page = 1 ) Pageable pageable
    ) {

        var pageRequest = PageRequest.of(pageable.getPageNumber()-1, pageable.getPageSize()
            , Sort.by(Direction.ASC, "createdAt"));
        CommentInfo.CommentsResponse childCommentList = commentFacade.getChildCommentList(commentId,
            pageRequest);
        var response = commentMapper.of(childCommentList);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/{comment_id}")
    @MemberOnly
    public ResponseEntity<CommentDto.CommentUpdateResponse> updateComment(
        @PathVariable("comment_id") Long commentId,
        @RequestBody CommentDto.CommentUpdateRequest commentUpdateRequest,
        @Auth final Accessor accessor
    ) {
        var command = commentMapper.of(commentUpdateRequest);
        commentFacade.updateComment(accessor.getMemberId(), commentId, command);
        return ResponseEntity.ok().body(new CommentDto.CommentUpdateResponse("댓글 수정 성공"));
    }

    @DeleteMapping("/{comment_id}")
    @MemberOnly
    public ResponseEntity<CommentDto.CommentDeleteResponse> deleteComment(
        @PathVariable("comment_id") Long commentId,
        @Auth final Accessor accessor
    ) {
        commentFacade.deleteComment(accessor.getMemberId(), commentId);
        return ResponseEntity.ok().body(new CommentDto.CommentDeleteResponse("댓글 삭제 성공"));
    }
}
