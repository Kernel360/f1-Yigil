package kr.co.yigil.comment.presentation;

import kr.co.yigil.auth.Auth;
import kr.co.yigil.auth.MemberOnly;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.comment.application.CommentService;
import kr.co.yigil.comment.dto.request.CommentCreateRequest;
import kr.co.yigil.comment.dto.response.CommentCreateResponse;
import kr.co.yigil.comment.dto.response.CommentDeleteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @PostMapping("/{travel_id}")
    @MemberOnly
    public ResponseEntity<CommentCreateResponse> createComment(
            @RequestBody CommentCreateRequest commentCreateRequest,
            @Auth final Accessor accessor,
            @PathVariable("travel_id") Long travelId
    ){
        CommentCreateResponse commentCreateResponse = commentService.createComment(accessor.getMemberId(), travelId, commentCreateRequest);
        return ResponseEntity.ok().body(commentCreateResponse);
    }

    @DeleteMapping("/{travel_id}/{comment_id}")
    @MemberOnly
    public ResponseEntity<CommentDeleteResponse> deleteComment(
            @PathVariable("comment_id") Long commentId,
            @PathVariable("travel_id") Long travelId,
            @Auth final Accessor accessor
    ){
        CommentDeleteResponse commentDeleteResponse = commentService.deleteComment(accessor.getMemberId(), travelId, commentId);
        return ResponseEntity.ok().body(commentDeleteResponse);
    }

}