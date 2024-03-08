package kr.co.yigil.comment.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import kr.co.yigil.comment.domain.CommentInfo.CommentNotiInfo;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.domain.MemberReader;
import kr.co.yigil.travel.domain.Travel;
import kr.co.yigil.travel.domain.TravelReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private CommentReader commentReader;
    @Mock
    private CommentStore commentStore;
    @Mock
    private MemberReader memberReader;
    @Mock
    private TravelReader travelReader;
    @Mock
    private CommentCountCacheStore commentCountCacheStore;

    @DisplayName("createComment 메서드가 실행됐을 때 CommentNotiInfo를 잘 반환하는지")
    @Test
    void whenCreateComment_thenShouldReturnCommentNotiInfo() {
        Long memberId = 1L;
        Long travelId = 1L;
        CommentCommand.CommentCreateRequest commentCreateRequest = new CommentCommand.CommentCreateRequest(
            "content", null);

        Member member = new Member(memberId, null, null, null, null, null);
        Travel travel = new Travel(travelId, member, null, null, 0, false);

        Comment mockComment = new Comment("content", member, travel, null);

        when(memberReader.getMember(memberId)).thenReturn(member);
        when(travelReader.getTravel(travelId)).thenReturn(travel);
        when(commentReader.findComment(commentCreateRequest.getParentId())).thenReturn(
            Optional.empty());
        when(commentStore.save(any())).thenReturn(mockComment);

        var result = commentService.createComment(memberId, travelId, commentCreateRequest);

        verify(commentCountCacheStore).increaseCommentCount(travelId);
        assertThat(result).isInstanceOf(CommentNotiInfo.class);
        assertThat(result.getNotificationMemberId(memberId)).isNull();
    }

    @DisplayName("createComment 메서드가 CommentNotiInfo를 잘 반환하는지")
    @Test
    void whenCreateComment_thenShouldReturnCommentNotiInfoAndItContainsParentCommentMemberId() {
        Long memberId = 1L;
        Long travelId = 1L;
        Long parentCommentMemberId = 500L;
        Long parentCommentId = 3L;

        CommentCommand.CommentCreateRequest commentCreateRequest = new CommentCommand.CommentCreateRequest(
            "content", parentCommentId);
        Member parentCommentMember = new Member(parentCommentMemberId, null, null, null, null,
            null);

        Member member = new Member(memberId, null, null, null, null, null);
        Travel travel = new Travel(travelId, member, null, null, 0, false);

        Comment parentComment = new Comment("content", parentCommentMember, travel, null);
        Comment mockComment = new Comment("content", member, travel, parentComment);

        when(memberReader.getMember(memberId)).thenReturn(member);
        when(travelReader.getTravel(travelId)).thenReturn(travel);
        when(commentReader.findComment(commentCreateRequest.getParentId())).thenReturn(
            Optional.empty());
        when(commentStore.save(any())).thenReturn(mockComment);

        var result = commentService.createComment(memberId, travelId, commentCreateRequest);

        verify(commentCountCacheStore).increaseCommentCount(travelId);
        assertThat(result).isInstanceOf(CommentNotiInfo.class);
        assertThat(result.getNotificationMemberId(memberId)).isEqualTo(parentCommentMemberId);
    }


    @DisplayName("deleteComment 메서드가 오류없이 정상적으로 잘 작동하는지")
    @Test
    void whenDeleteComment_thenShouldNotThrowAnError() {

        Travel travel = new Travel(1L, null, null, null, 0, false);
        Comment comment = new Comment("content", null, travel, null);
        when(commentReader.getCommentWithMemberId(1L, 1L)).thenReturn(comment);
        when(commentReader.getTravelIdByCommentId(1L)).thenReturn(1L);

        commentService.deleteComment(1L, 1L);

        verify(commentStore).delete(comment);
        verify(commentCountCacheStore).decreaseCommentCount(1L);
    }

    @DisplayName("getParentComments 메서드가 CommentsResponse를 잘 반환하는지")
    @Test
    void whenGetParentComments_thenShouldReturnCommentsResponse() {
        Long travelId = 1L;
        var pageable = PageRequest.of(0, 10);

        Member member1 = mock(Member.class);
        Member member2 = mock(Member.class);

        Comment mockComment1 = new Comment("content1", member1, null, null);
        Comment mockComment2 = new Comment("content2", member2, null, null);

        int childCount1 = 5;
        int childCount2 = 3;

        Slice<Comment> mockSlice = new SliceImpl<>(List.of(mockComment1, mockComment2), pageable,
            true);
        when(commentReader.getParentCommentsByTravelId(travelId, pageable)).thenReturn(mockSlice);
        when(commentReader.getChildrenCommentCount(mockComment1.getId())).thenReturn(childCount1);
        when(commentReader.getChildrenCommentCount(mockComment2.getId())).thenReturn(childCount2);

        var result = commentService.getParentComments(travelId, pageable);

        assertThat(result).isInstanceOf(CommentInfo.CommentsResponse.class);
        assertThat(result.getContent()).hasSize(2);
    }

    @DisplayName("getChildComments 메서드가 CommentsResponse를 잘 반환하는지")
    @Test
    void whenGetChildComments_thenShouldReturnCommentResponse() {
        Long parentId = 1L;
        var pageable = PageRequest.of(0, 10);

        Member member1 = mock(Member.class);
        Member member2 = mock(Member.class);
        Comment mockParentComment = mock(Comment.class);
        Comment mockComment1 = new Comment("content1", member1, null, mockParentComment);
        Comment mockComment2 = new Comment("content2", member2, null, mockParentComment);
        Slice<Comment> mockSlice = new SliceImpl<>(List.of(mockComment1, mockComment2), pageable,
            true);

        when(commentReader.getChildCommentsByParentId(parentId, pageable)).thenReturn(mockSlice);

        var result = commentService.getChildComments(parentId, pageable);

        assertThat(result).isInstanceOf(CommentInfo.CommentsResponse.class);
        assertThat(result.getContent()).hasSize(2);
    }

    @DisplayName("updateComment 메서드가 CommentNotiInfo를 잘 반환하는지")
    @Test
    void whenUpdateComment_thenShouldReturnCommentNotiInfo() {
        Long commentId = 1L;
        Long memberId = 1L;
        CommentCommand.CommentUpdateRequest command = new CommentCommand.CommentUpdateRequest(
            "content");

        Member sameMember = new Member(memberId, null, null, null, null, null);
        Travel mockTravel = new Travel(1L, sameMember, null, null, 0, false);
        Comment mockComment = new Comment("content", sameMember, mockTravel, null);

        when(commentReader.getCommentWithMemberId(commentId, memberId)).thenReturn(mockComment);

        var result = commentService.updateComment(commentId, memberId, command);

        assertThat(result).isInstanceOf(CommentNotiInfo.class);
        assertThat(result.getNotificationMemberId(memberId)).isNull();
    }
}