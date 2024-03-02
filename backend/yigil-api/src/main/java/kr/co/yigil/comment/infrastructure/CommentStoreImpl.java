package kr.co.yigil.comment.infrastructure;

import kr.co.yigil.comment.domain.Comment;
import kr.co.yigil.comment.domain.CommentStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CommentStoreImpl implements CommentStore {

    private final CommentRepository commentRepository;

    @Override
    public void save(Comment newComment) {
        commentRepository.save(newComment);
    }

    @Override
    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }
}
