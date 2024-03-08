package kr.co.yigil.comment.infrastructure;

import kr.co.yigil.comment.domain.CommentReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentReaderImpl implements CommentReader {
    private final CommentRepository commentRepository;

    @Override
    public int getCommentCount(Long travelId) {
        return commentRepository.countAllByTravelIdAndIsDeletedFalse(travelId);
    }


}
