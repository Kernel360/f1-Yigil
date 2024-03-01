package kr.co.yigil.comment.application;

import kr.co.yigil.comment.domain.CommentCommand;
import kr.co.yigil.comment.domain.CommentCommand.CommentUpdateRequest;
import kr.co.yigil.comment.domain.CommentInfo;
import kr.co.yigil.comment.domain.CommentService;
import kr.co.yigil.notification.domain.NotificationService;
import kr.co.yigil.notification.domain.NotificationType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentFacade {

    private final CommentService commentService;
    private final NotificationService notificationService;

    @Transactional
    public CommentInfo.CommentCreateResponse createComment(Long memberId, Long travelId,
        CommentCommand.CommentCreateRequest commentCreateRequest) {
        var response = commentService.createComment(memberId, travelId, commentCreateRequest);
        notificationService.sendNotification(NotificationType.NEW_COMMENT, memberId,
            response.getNotifiedReceiverId());
        return new CommentInfo.CommentCreateResponse("댓글 생성 성공");
    }

    @Transactional(readOnly = true)
    public CommentInfo.CommentsResponse getParentCommentList(Long travelId, Pageable pageable) {

//        Slice<Comment> comments = commentService.findParentCommentsByTravelId(travelId, pageable);
//        List<CommentResponse> commentResponses = comments.stream().map(CommentResponse::from)
//                .toList();
//
//        return new SliceImpl<>(commentResponses, pageable, comments.hasNext());
        return commentService.getParentComments(travelId, pageable);
    }

    @Transactional(readOnly = true)
    public CommentInfo.CommentsResponse getChildCommentList(Long parentId, Pageable pageable) {
//        Slice<Comment> comments = commentService.findChildCommentsByParentId(parentId, pageable);
//        List<CommentResponse> commentResponses = comments.stream().map(CommentResponse::from)
//                .toList();
//        return new CommentsResponse(commentResponses, pageable, comments.hasNext());
        return commentService.getChildComments(parentId, pageable);
    }

    @Transactional
    public CommentInfo.DeleteResponse deleteComment(Long memberId, Long commentId) {
        commentService.deleteComment(commentId, memberId);
        //notif 필요?
        return new CommentInfo.DeleteResponse("댓글 삭제 성공");
    }

    public void updateComment(Long memberId, Long commentId,
        CommentUpdateRequest command) {
        var response = commentService.updateComment(commentId, memberId, command);

        notificationService.sendNotification(NotificationType.NEW_COMMENT, memberId, response.getNotifiedReceiverId());
    }
}

