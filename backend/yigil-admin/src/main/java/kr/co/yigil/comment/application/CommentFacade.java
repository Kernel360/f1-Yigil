package kr.co.yigil.comment.application;

import kr.co.yigil.admin.domain.admin.AdminService;
import kr.co.yigil.comment.domain.CommentInfo.ChildrenPageComments;
import kr.co.yigil.comment.domain.CommentInfo.CommentList;
import kr.co.yigil.comment.domain.CommentInfo.ParentPageComments;
import kr.co.yigil.comment.domain.CommentService;
import kr.co.yigil.notification.domain.NotificationService;
import kr.co.yigil.notification.domain.NotificationType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentFacade {

    private final CommentService commentService;
    private final NotificationService notificationService;
    private final AdminService adminService;

    public CommentList getComments(Long travelId, PageRequest pageRequest) {
        return commentService.getComments(travelId, pageRequest);
    }

    public ParentPageComments getParentComments(Long travelId, PageRequest pageRequest) {
        return commentService.getParentComments(travelId, pageRequest);
    }
    public ChildrenPageComments getChildrenComments(Long travelId, PageRequest pageRequest) {
        return commentService.getChildrenComments(travelId, pageRequest);
    }

    public void deleteComment(Long commentId) {
        Long memberId = commentService.deleteComment(commentId);
        Long adminId = adminService.getAdminId();
        notificationService.sendNotification(NotificationType.COMMENT_DELETE, adminId, memberId);
    }

}
