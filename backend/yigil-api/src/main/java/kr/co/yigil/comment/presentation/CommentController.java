package kr.co.yigil.comment.presentation;

import java.util.List;
import kr.co.yigil.auth.Auth;
import kr.co.yigil.auth.MemberOnly;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.comment.application.CommentService;
import kr.co.yigil.comment.dto.request.CommentCreateRequest;
import kr.co.yigil.comment.dto.response.CommentCreateResponse;
import kr.co.yigil.comment.dto.response.CommentDeleteResponse;
import kr.co.yigil.comment.dto.response.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{post_id}")
    @MemberOnly
    public ResponseEntity<CommentCreateResponse> createComment(
            @RequestBody CommentCreateRequest commentCreateRequest,
            @Auth final Accessor accessor,
            @PathVariable("post_id") Long postId
    ){
        CommentCreateResponse commentCreateResponse = commentService.createComment(accessor.getMemberId(), postId, commentCreateRequest);
        return ResponseEntity.ok().body(commentCreateResponse);
    }
    @GetMapping("/{post_id}")
    public ResponseEntity<List<CommentResponse>> getTopCommentList(
        @PathVariable("post_id") Long postId,
        @PageableDefault(page = 0, size = 5)
        Pageable pageable

    ){
        List<CommentResponse> commentListResponse = commentService.getTopLevelCommentList(postId);
        return ResponseEntity.ok().body(commentListResponse);
    }

    @GetMapping("/{post_id}/{comment_id}")
    public ResponseEntity<List<CommentResponse>> getReplyCommentList(
        @PathVariable("post_id") Long postId,
        @PathVariable("comment_id") Long commentId
    ){
        List<CommentResponse> commentListResponse = commentService.getReplyCommentList(postId, commentId);
        return ResponseEntity.ok().body(commentListResponse);
    }

    @DeleteMapping("/{post_id}/{comment_id}")
    @MemberOnly
    public ResponseEntity<CommentDeleteResponse> deleteComment(
            @PathVariable("comment_id") Long commentId,
            @PathVariable("post_id") Long postId,
            @Auth final Accessor accessor
    ){
        CommentDeleteResponse commentDeleteResponse = commentService.deleteComment(accessor.getMemberId(), postId, commentId);
        return ResponseEntity.ok().body(commentDeleteResponse);
    }
}
