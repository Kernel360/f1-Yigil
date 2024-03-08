package kr.co.yigil.comment.infrastructure;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import kr.co.yigil.comment.domain.Comment;
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
class CommentReaderImplTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentReaderImpl commentReader;


    @DisplayName("댓글 수를 조회할 때 댓글 수를 잘 반환하는지")
    @Test
    void whenGetCommentCount_shouldReturnCommentcount() {

        int commentCount = 3;
        when(commentRepository.countAllByTravelIdAndIsDeletedFalse(1L)).thenReturn(commentCount);

        var result = commentReader.getCommentCount(1L);

        assertThat(result).isEqualTo(commentCount);
    }

    @DisplayName("댓글을 조회할 때 댓글을 잘 반환하는지")
    @Test
    void whenGetParentComments_thenShouldReturnPageComment() {
        Comment comment1 = mock(Comment.class);
        Comment comment2 = mock(Comment.class);
        List<Comment> comments = List.of(comment1, comment2);

        Page<Comment> pageComments = new PageImpl<>(comments);

        when(commentRepository.findAllAsPageImplByTravelIdAndParentIdIsNull(1L, null)).thenReturn(
            pageComments);

        var result = commentReader.getParentComments(1L, null);

        assertThat(result).isEqualTo(pageComments);
    }

    @DisplayName("대댓글을 조회할 때 대댓글을 잘 반환하는지")
    @Test
    void whenGetChildrenComments_thenShouldReturnPageComment() {

        Comment comment1 = mock(Comment.class);
        Comment comment2 = mock(Comment.class);
        List<Comment> comments = List.of(comment1, comment2);

        Page<Comment> pageComments = new PageImpl<>(comments);

        when(commentRepository.findAllByParentIdAndIsDeletedFalse(anyLong(),
            any(PageRequest.class))).thenReturn(pageComments);

        var result = commentReader.getChildrenComments(1L, mock(PageRequest.class));

        assertThat(result).isEqualTo(pageComments);
    }

    @DisplayName("대댓글 수를 조회할 때 대댓글 수를 잘 반환하는지")
    @Test
    void whenGetChildrenCount_thenShouldReturnChildrenCount() {

        int childrenCount = 3;
        when(commentRepository.countByParentId(1L)).thenReturn(childrenCount);

        var result = commentReader.getChildrenCount(1L);

        assertThat(result).isEqualTo(childrenCount);
    }

    @DisplayName("댓글을 조회할 때 댓글을 잘 반환하는지")
    @Test
    void whenGetComment_thenShouldReturnComment() {
        Comment comment = mock(Comment.class);

        when(commentRepository.findById(1L)).thenReturn(java.util.Optional.of(comment));

        var result = commentReader.getComment(1L);

        assertThat(result).isEqualTo(comment);
    }

    @DisplayName("대댓글을 조회할 때 대댓글을 잘 반환하는지")
    @Test
    void whenGetChildrenComments_thenShouldReturnListComment() {
        Comment comment1 = mock(Comment.class);
        Comment comment2 = mock(Comment.class);
        List<Comment> comments = List.of(comment1, comment2);

        when(commentRepository.findAllByParentIdAndIsDeletedFalse(1L)).thenReturn(comments);

        var result = commentReader.getChildrenComments(1L);

        assertThat(result).isEqualTo(comments);
    }
}