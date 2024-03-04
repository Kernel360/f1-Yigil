package kr.co.yigil.comment.infrastructure;

import kr.co.yigil.comment.domain.Comment;
import kr.co.yigil.comment.domain.CommentStore;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CommentStoreImpl implements CommentStore {

    private final CommentRepository commentRepository;

    @Override
    public Comment save(Comment newComment) {
        return commentRepository.save(newComment);
    }

    @Override
    public void delete(Comment comment) {
        if(comment.isDeleted()) throw new BadRequestException(ExceptionCode.ALREADY_REMOVED_COMMENT);
        commentRepository.delete(comment);
    }
}
