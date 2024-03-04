package kr.co.yigil.comment.infrastructure;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;
import kr.co.yigil.comment.domain.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;

@ExtendWith(MockitoExtension.class)
class CommentReaderImplTest {

    @Mock
    private CommentRepository commentRepository;
    @InjectMocks
    private CommentReaderImpl commentReader;

    @DisplayName("findComment 메서드가 Comment의 Optional 객체를 잘 반환하는지")
    @Test
    void whenFindComment_thenShouldReturnOptionalComment() {

        Comment mockComment = mock(Comment.class);
        when(commentRepository.findById(1L)).thenReturn(Optional.of(mockComment));

        var result = commentReader.findComment(1L);
        assertThat(result).isPresent()
            .isEqualTo(Optional.of(mockComment));
    }

    @DisplayName("getCommentWithMemberId 메서드가 Comment를 잘 반환하는지")
    @Test
    void whenGetCommentWithMemberId_thenShouldReturnComment() {
        Comment mockComment = mock(Comment.class);
        when(commentRepository.findByIdAndMemberId(1L, 1L)).thenReturn(Optional.of(mockComment));

        var result = commentReader.getCommentWithMemberId(1L, 1L);
        assertThat(result).isEqualTo(mockComment);
    }

    @DisplayName("getCommentsByTravelId 메서드가 Slice<Comment>를 잘 반환하는지")
    @Test
    void whenGetCommentsByTravelId_thenShouldRerturnCommentOfSlice() {

        when(commentRepository.findAllByTravelIdAndParentIsNull(anyLong(), any(Pageable.class)))
            .thenReturn(new SliceImpl<>(Arrays.asList(mock(Comment.class))));

        var result = commentReader.getCommentsByTravelId(1L, Pageable.unpaged());

        assertThat(result).isNotNull();
        assertThat(result.getContent().getFirst()).isInstanceOf(Comment.class);

    }

    @DisplayName("getParentCommentsByTravelId 메서드가 Slice<Comment>를 잘 반환하는지")
    @Test
    void whenGetParentCommentsByTravelId_thenShouldReturnCommentOfSlice() {
        when(commentRepository.findAllByTravelIdAndParentIsNull(anyLong(), any(Pageable.class)))
            .thenReturn(new SliceImpl<>(Arrays.asList(mock(Comment.class))));

        var result = commentReader.getParentCommentsByTravelId(1L, Pageable.unpaged());

        assertThat(result).isNotNull();
        assertThat(result.getContent().getFirst()).isInstanceOf(Comment.class);
    }

    @DisplayName("getChildCommentsByParentId 메서드가 Slice<Comment>를 잘 반환하는지")
    @Test
    void whenGetChildCommentsByParentId_thenShouldReturnCommentOfSlice() {
        when(commentRepository.findChildCommentsByParentId(anyLong(), any(Pageable.class))
        ).thenReturn(new SliceImpl<>(Arrays.asList(mock(Comment.class))));

        var result = commentReader.getChildCommentsByParentId(1L, Pageable.unpaged());

        assertThat(result).isNotNull();
        assertThat(result.getContent().getFirst()).isInstanceOf(Comment.class);
    }

    @DisplayName("getCommentCount 메서드가 travel의 댓글 수를 잘 반환하는지")
    @Test
    void whenGetCommentCount_thenShouldReturnTravelsCommentCounts() {
        int commentCount = 100;
        when(commentRepository.countAllByTravelIdAndIsDeletedFalse(anyLong())).thenReturn(commentCount);

        var result = commentReader.getCommentCount(1L);

        assertThat(result).isEqualTo(commentCount);

    }

    @DisplayName("getTravelIdByCommentId 메서드가 comment가 달린 글의 Id를 잘 반환하는지")
    @Test
    void whenGetTravelIdByCommentId_thenShouldReturnTravelId() {
        Long travelId = 1L;
        when(commentRepository.findTravelIdByCommentId(anyLong())).thenReturn(Optional.of(travelId));

        var result = commentReader.getTravelIdByCommentId(1L);

        assertThat(result).isEqualTo(travelId);

    }

    @DisplayName("getChildrenCommentCount 메서드가 자식 댓글의 수를 잘 반환하는지")
    @Test
    void whenGetChildrenCommentCount_thenShouldReturn() {
        int childrenCommentCount = 100;
        when(commentRepository.countByParentId(anyLong())).thenReturn(childrenCommentCount);

        var result = commentReader.getChildrenCommentCount(1L);

        assertThat(result).isEqualTo(childrenCommentCount);
    }
}