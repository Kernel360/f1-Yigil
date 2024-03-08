package kr.co.yigil.comment.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import kr.co.yigil.comment.domain.CommentInfo.ChildrenPageComments;
import kr.co.yigil.comment.domain.CommentInfo.ParentPageComments;
import kr.co.yigil.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    private CommentReader commentReader;

    @Mock
    private CommentStore commentStore;

    @InjectMocks
    private CommentServiceImpl commentServiceImpl;


    @DisplayName("getParentComments 메서드가 CommentReader를 잘 호출하고 ParentPageComments를 반환하는지")
    @Test
    void whenGetParentComments_thenShouldReturnParentPageComments() {

        Long travelId = 1L;
        PageRequest pageRequest = PageRequest.of(0, 5);
        Member mockMember = new Member(1L, null, null, "nickname", null, null);
        Comment mockComment = mock(Comment.class);
        when(mockComment.getId()).thenReturn(1L);
        when(mockComment.getMember()).thenReturn(mockMember);
        when(mockComment.getContent()).thenReturn("content");
        when(mockComment.getCreatedAt()).thenReturn(null);
        List<Comment> comments = List.of(mockComment);
        Page<Comment> pageComments = new PageImpl<>(comments, pageRequest, comments.size());

        when(commentReader.getParentComments(travelId, pageRequest)).thenReturn(pageComments);
        when(commentReader.getChildrenCount(anyLong())).thenReturn(1);

        // When
        ParentPageComments response = commentServiceImpl.getParentComments(travelId, pageRequest);

        // Then
        assertThat(response).isInstanceOf(ParentPageComments.class);
    }

    @DisplayName("getChildrenComments 메서드가 CommentReader를 잘 호출하고 ChildrenPageComments를 반환하는지")
    @Test
    void whenGetChildrenComments_thenShouldReturnChildrenPageComments() {

        Long parentId = 1L;
        PageRequest pageRequest = PageRequest.of(0, 5);
        Member mockMember = new Member(1L, null, null, "nickname", null, null);
        Comment mockComment = mock(Comment.class);
        when(mockComment.getId()).thenReturn(1L);
        when(mockComment.getMember()).thenReturn(mockMember);
        when(mockComment.getContent()).thenReturn("content");
        when(mockComment.getCreatedAt()).thenReturn(null);
        List<Comment> comments = List.of(mockComment);
        Page<Comment> pageComments = new PageImpl<>(comments, pageRequest, comments.size());

        when(commentReader.getChildrenComments(parentId, pageRequest)).thenReturn(pageComments);

        // When
        CommentInfo.ChildrenPageComments response = commentServiceImpl.getChildrenComments(parentId, pageRequest);

        // Then
        assertThat(response).isInstanceOf(ChildrenPageComments.class);

    }

    @DisplayName("deleteComment 메서드가 CommentReader를 잘 호출하고 memberId를 반환하는지")
    @Test
    void whenDeleteComment_thenShouldReturnMemberId() {

        Long commentId = 1L;
        Member mockMember = new Member(1L, null, null, "nickname", null, null);
        Comment mockComment = mock(Comment.class);

        when(mockComment.getMember()).thenReturn(mockMember);
        when(commentReader.getComment(commentId)).thenReturn(mockComment);

        // When
        Long response = commentServiceImpl.deleteComment(commentId);

        // Then
        assertThat(response).isEqualTo(1L);

    }
}