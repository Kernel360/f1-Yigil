package kr.co.yigil.comment.application;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import kr.co.yigil.comment.domain.Comment;
import kr.co.yigil.comment.domain.CommentCommand;
import kr.co.yigil.comment.domain.CommentInfo.CommentNotiInfo;
import kr.co.yigil.comment.domain.CommentService;
import kr.co.yigil.member.Member;
import kr.co.yigil.notification.domain.NotificationService;
import kr.co.yigil.notification.domain.NotificationType;
import kr.co.yigil.travel.domain.Travel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class CommentFacadeTest {

    @InjectMocks
    private CommentFacade commentFacade;

    @Mock
    private CommentService commentService;

    @Mock
    private NotificationService notificationService;

    @DisplayName("createComment 메서드가 유효한 요청이 들어왔을 때 CommentService의 createComment 메서드와 알림을 잘 호출하는지")
    @Test
    void whenCreateComment_thenShouldNotThrowAnError() {
        // given
        Long commentMemberId = 3L;
        Long travelId = 1L;
        Long travelMemberId = 1L;
        Long parentId = null;

        var commentCreateRequest = CommentCommand.CommentCreateRequest.builder()
            .content("content")
            .parentId(parentId)
            .build();
        Member traveMember = new Member(travelMemberId, null, null, null, null, null);
        Member commentMember = new Member(commentMemberId, null, null, null, null, null);
        Travel travel = new Travel(travelId, traveMember, null, null, 0, false);
        Comment createdComment = new Comment("child content", commentMember, travel,  null);

        CommentNotiInfo commentNotiInfo = new CommentNotiInfo(createdComment);
        when(commentService.createComment(commentMemberId, travelId, commentCreateRequest))
            .thenReturn(commentNotiInfo);

        commentFacade.createComment(commentMemberId, travelId, commentCreateRequest);

        verify(notificationService).sendNotification(NotificationType.NEW_COMMENT, commentMemberId, travelMemberId);

    }

    @DisplayName("createComment 메서드가 유효한 요청이 들어왔을 때 CommentService의 createComment 메서드와 알림을 잘 호출하는지")
    @Test
    void whenCreateComment_thenShouldNotThrowAnError2() {
        // given
        Long commentMemberId = 3L;
        Long travelId = 1L;
        Long travelMemberId = 1L;
        Long parentCommentMemberId = 2L;
        Long parentId = 2L;

        var commentCreateRequest = CommentCommand.CommentCreateRequest.builder()
            .content("content")
            .parentId(parentId)
            .build();
        Member traveMember = new Member(travelMemberId, null, null, null, null, null);
        Member parentMember = new Member(parentCommentMemberId, null, null, null, null, null);
        Member commentMember = new Member(commentMemberId, null, null, null, null, null);

        Travel travel = new Travel(travelId, traveMember, null, null, 0, false);
        Comment parentComment = new Comment("parent content", parentMember, travel,  null);
        Comment createdComment = new Comment("child content", commentMember, travel,  parentComment);

        CommentNotiInfo commentNotiInfo = new CommentNotiInfo(createdComment);
        when(commentService.createComment(commentMemberId, travelId, commentCreateRequest))
            .thenReturn(commentNotiInfo);

        commentFacade.createComment(commentMemberId, travelId, commentCreateRequest);

        verify(notificationService).sendNotification(NotificationType.NEW_COMMENT, commentMemberId, parentCommentMemberId);
    }

    @DisplayName("getParentCommentList 메서드가 유효한 요청이 들어왔을 때 응답을 잘 주는지")
    @Test
    void whenGetParentCommentList_thenShouldReturnCommentResponse() {
        // given
        Long travelId = 1L;
        var pageable = PageRequest.of(0, 5);

        // when
        commentFacade.getParentCommentList(travelId, pageable);

        // then
        verify(commentService).getParentComments(travelId, pageable);


    }

    @DisplayName("getChildCommentList 메서드가 유효한 요청이 들어왔을 때 응답을 잘 주는지")
    @Test
    void WhenGetChildCommentList_thenShouldReturnCommentResponse() {
        // given
        Long parentId = 1L;
        var pageable = PageRequest.of(0, 5);

        // when
        commentFacade.getChildCommentList(parentId, pageable);

        // then
        verify(commentService).getChildComments(parentId, pageable);
    }

    @DisplayName("deleteComment 메서드가 유효한 요청이 들어왔을 때 CommentService의 deleteComment 메서드를 잘 호출하는지")
    @Test
    void whenDeleteComment_thenShouldCallCommentService() {
        // given
        Long memberId = 1L;
        Long commentId = 1L;

        // when
        commentFacade.deleteComment(memberId, commentId);

        // then
        verify(commentService).deleteComment(memberId, commentId);

    }

    @DisplayName("updateComment 메서드가 유효한 요청이 들어왔을 때 CommentService의 updateComment 메서드를 잘 호출하는지")
    @Test
    void updateComment() {
        // given
        Long commentMemberId = 3L;
        Long commentId = 1L;
        Long travelId = 1L;
        Long travelMemberId = 1L;

        var command = CommentCommand.CommentUpdateRequest.builder()
            .content("content")
            .build();
        Member traveMember = new Member(travelMemberId, null, null, null, null, null);
        Member commentMember = new Member(commentMemberId, null, null, null, null, null);

        Travel travel = new Travel(travelId, traveMember, null, null, 0, false);

        Comment createdComment = new Comment("child content", commentMember, travel,  null);

        CommentNotiInfo commentNotiInfo = new CommentNotiInfo(createdComment);

        when(commentService.updateComment(commentId, commentMemberId, command))
            .thenReturn(commentNotiInfo);

        // when
        commentFacade.updateComment(commentMemberId, commentId, command);

        // then

        verify(notificationService).sendNotification(NotificationType.UPDATE_COMMENT, commentMemberId, travelMemberId);

    }
    @DisplayName("updateComment 메서드가 유효한 요청이 들어왔을 때 CommentService의 updateComment 메서드를 잘 호출하는지")
    @Test
    void whenUpdateComment_givenChildrenCommentId_thenShouldNotifyToParentComment() {
        // given
        Long commentMemberId = 3L;
        Long commentId = 1L;
        Long travelId = 1L;
        Long travelMemberId = 1L;
        Long parentCommentMemberId = 2L;

        var command = CommentCommand.CommentUpdateRequest.builder()
            .content("content")
            .build();
        Member traveMember = new Member(travelMemberId, null, null, null, null, null);
        Member parentMember = new Member(parentCommentMemberId, null, null, null, null, null);
        Member commentMember = new Member(commentMemberId, null, null, null, null, null);

        Travel travel = new Travel(travelId, traveMember, null, null, 0, false);
        Comment parentComment = new Comment("parent content", parentMember, travel,  null);
        Comment createdComment = new Comment("child content", commentMember, travel,  parentComment);

        CommentNotiInfo commentNotiInfo = new CommentNotiInfo(createdComment);

        when(commentService.updateComment(commentId, commentMemberId, command))
            .thenReturn(commentNotiInfo);

        commentFacade.updateComment(commentMemberId, commentId, command);

        verify(notificationService).sendNotification(NotificationType.UPDATE_COMMENT, commentMemberId, parentCommentMemberId);
    }
}