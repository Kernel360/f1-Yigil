package kr.co.yigil.comment.infrastructure;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import kr.co.yigil.comment.domain.Comment;
import kr.co.yigil.global.exception.BadRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class CommentStoreImplTest {
    @InjectMocks
    private CommentStoreImpl commentStore;
    @Mock
    private CommentRepository commentRepository;

    @DisplayName("save 메서드가 잘 동작하는지")
    @Test
    void WhenSave_thenShouldReturnSavedComment() {
        Comment comment = mock(Comment.class);
        when(commentRepository.save(comment)).thenReturn(comment);

        Comment savedComment = commentStore.save(comment);

        assertThat(savedComment).isEqualTo(comment);
    }

    @DisplayName("delete 메서드가 잘 동작하는지")
    @Test
    void whenDelete_thenShouldNotThrownAnError() {
        Comment comment = mock(Comment.class);
        when(comment.isDeleted()).thenReturn(false);

        commentStore.delete(comment);
    }

    @DisplayName("delete 메서드가 삭제된 댓글을 삭제하려고 할 때 예외를 잘 발생시키는지")
    @Test
    void whenDelete_thenShouldThrownAnError() {
        Comment comment = mock(Comment.class);
        when(comment.isDeleted()).thenReturn(true);

        assertThatThrownBy(() -> commentStore.delete(comment)).isInstanceOf(BadRequestException.class);
    }
}