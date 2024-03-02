package kr.co.yigil.comment.application;

import java.util.Optional;
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
    public void createComment(Long memberId, Long travelId,
        CommentCommand.CommentCreateRequest commentCreateRequest) {

        var createdCommentInfo = commentService.createComment(memberId, travelId,
            commentCreateRequest);

        Optional<Long> notifiedMemberId = createdCommentInfo.getNotificationMemberId(memberId);
        notifiedMemberId.ifPresent(
            id -> notificationService.sendNotification(NotificationType.NEW_COMMENT, memberId, id)
        );
    }

    @Transactional(readOnly = true)
    public CommentInfo.CommentsResponse getParentCommentList(Long travelId, Pageable pageable) {

        return commentService.getParentComments(travelId, pageable);
    }

    @Transactional(readOnly = true)
    public CommentInfo.CommentsResponse getChildCommentList(Long parentId, Pageable pageable) {
        return commentService.getChildComments(parentId, pageable);
    }

    @Transactional
    public void deleteComment(Long memberId, Long commentId) {
        commentService.deleteComment(memberId, commentId);
    }

    public void updateComment(Long memberId, Long commentId,
        CommentUpdateRequest command) {
        var updatedCommentInfo = commentService.updateComment(commentId, memberId, command);

        Optional<Long> notifiedMemberId = updatedCommentInfo.getNotificationMemberId(memberId);
        notifiedMemberId.ifPresent(
            id -> notificationService.sendNotification(NotificationType.UPDATE_COMMENT, memberId, id)
        );
    }
}

