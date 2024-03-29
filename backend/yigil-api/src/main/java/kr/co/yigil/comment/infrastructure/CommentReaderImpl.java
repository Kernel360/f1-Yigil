package kr.co.yigil.comment.infrastructure;


import java.util.Optional;
import kr.co.yigil.comment.domain.Comment;
import kr.co.yigil.comment.domain.CommentReader;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentReaderImpl implements CommentReader {
    private final CommentRepository commentRepository;

    @Override
    public Optional<Comment> findComment(Long commentId) {
        if(commentId == null) return Optional.empty();
        return commentRepository.findById(commentId);
    }

    @Override
    public Comment getCommentWithMemberId(Long commentId, Long memberId) {
        return commentRepository.findByIdAndMemberId(commentId, memberId).orElseThrow(
            () -> new BadRequestException(ExceptionCode.INVALID_AUTHORITY));
    }

    @Override
    public Slice<Comment> getCommentsByTravelId(Long travelId, Pageable pageable) {
        return commentRepository.findAllByTravelIdAndParentIsNull(travelId, pageable);
    }

    @Override
    public Slice<Comment> getParentCommentsByTravelId(Long travelId, Pageable pageable) {
        return commentRepository.findAllByTravelIdAndParentIsNull(travelId, pageable);
    }

    @Override
    public Slice<Comment> getChildCommentsByParentId(Long parentId, Pageable pageable) {
        return commentRepository.findChildCommentsByParentId(parentId, pageable);
    }

    @Override
    public int getCommentCount(Long travelId) {
        return commentRepository.countAllByTravelIdAndIsDeletedFalse(travelId);
    }

    @Override
    public Long getTravelIdByCommentId(Long commentId) {
        return commentRepository.findTravelIdByCommentId(commentId).orElseThrow(
            () -> new BadRequestException(ExceptionCode.NOT_FOUND_TRAVEL_ID));
    }

    @Override
    public int getChildrenCommentCount(Long parentId) {
        return commentRepository.countByParentId(parentId);
    }
}