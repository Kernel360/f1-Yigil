package kr.co.yigil.comment.infrastructure;

import kr.co.yigil.comment.domain.Comment;
import kr.co.yigil.comment.domain.CommentReader;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentReaderImpl implements CommentReader {

    private final CommentRepository commentRepository;

    @Override
    public int getCommentCount(Long travelId) {
        return commentRepository.countAllByTravelIdAndIsDeletedFalse(travelId);
    }

    @Override
    public Page<Comment> getParentComments(Long travelId, PageRequest pageRequest) {
        return commentRepository.findAllAsPageImplByTravelIdAndParentIdIsNull(travelId,
            pageRequest);
    }

    @Override
    public Page<Comment> getChildrenComments(Long travelId, PageRequest pageRequest) {
        return commentRepository.findAllByParentIdAndIsDeletedFalse(travelId, pageRequest);
    }

    @Override
    public int getChildrenCount(Long parentId) {
        return commentRepository.countByParentId(parentId);
    }

    @Override
    public Comment getComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
            () -> new BadRequestException(ExceptionCode.NOT_FOUND_COMMENT_ID));
    }

}
