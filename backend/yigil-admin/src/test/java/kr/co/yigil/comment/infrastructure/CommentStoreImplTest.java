package kr.co.yigil.comment.infrastructure;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import kr.co.yigil.comment.domain.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentStoreImplTest {

    @Mock
    private CommentRepository commentRepository;
    @InjectMocks
    private CommentStoreImpl commentStore;

    @DisplayName("댓글을 삭제할 때 댓글을 삭제하는지")
    @Test
    void deleteComment() {
        Comment comment = mock(Comment.class);

        commentStore.deleteComment(comment);

        verify(commentRepository).delete(comment);
    }
}