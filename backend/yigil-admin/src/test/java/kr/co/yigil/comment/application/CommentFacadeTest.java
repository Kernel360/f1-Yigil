package kr.co.yigil.comment.application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import kr.co.yigil.admin.domain.admin.AdminService;
import kr.co.yigil.comment.domain.CommentInfo.ChildrenPageComments;
import kr.co.yigil.comment.domain.CommentInfo.CommentList;
import kr.co.yigil.comment.domain.CommentInfo.ParentPageComments;
import kr.co.yigil.comment.domain.CommentService;
import kr.co.yigil.notification.domain.NotificationService;
import kr.co.yigil.notification.domain.NotificationType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class CommentFacadeTest {

    @Mock
    private CommentService commentService;
    @Mock
    private NotificationService notificationService;
    @Mock
    private AdminService adminService;

    @InjectMocks
    private CommentFacade commentFacade;


    @DisplayName("getParentComments 메서드가 ParentPageComments를 잘 반환하는지")
    @Test
    void whenGetParentComments_thenShouldReturnParentPagecomment() {
        when(commentService.getParentComments(anyLong(), any(PageRequest.class))).thenReturn(mock(ParentPageComments.class));

        var response = commentFacade.getParentComments(1L, PageRequest.of(0, 10));

        assertThat(response).isInstanceOf(ParentPageComments.class);
    }

    @DisplayName("getChildrenComments 메서드가 ChildrenPageComments를 잘 반환하는지")
    @Test
    void whenGetChildrenComments_thenShouldReturnChildrenPageComments() {

        when(commentService.getChildrenComments(anyLong(), any(PageRequest.class))).thenReturn(mock(
            ChildrenPageComments.class));

        var response = commentFacade.getChildrenComments(1L, PageRequest.of(0, 10));

        assertThat(response).isInstanceOf(ChildrenPageComments.class);
    }

    @DisplayName("deleteComment 메서드가 잘 호출되는지")
    @Test
    void whenDeleteComment_thenShoudSendNotification() {

        Long adminId = 1L;
        Long memberId = 3L;
        Long commentId = 2L;
        when(commentService.deleteComment(anyLong())).thenReturn(memberId);
        when(adminService.getAdminId()).thenReturn(adminId);

        commentFacade.deleteComment(commentId);

        verify(notificationService).saveNotification(NotificationType.COMMENT_DELETE, adminId, memberId);
    }

    @DisplayName("getComments 메서드가 잘 호출되는지")
    @Test
    void whenGetComments_thenShouldReturnCommentList() {
        when(commentService.getComments(anyLong(), any(PageRequest.class))).thenReturn(mock(CommentList.class));

        var response = commentFacade.getComments(1L, PageRequest.of(0, 10));

        assertThat(response).isInstanceOf(CommentList.class);
    }
}