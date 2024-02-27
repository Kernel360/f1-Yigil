package kr.co.yigil.comment.application;

import java.util.Optional;
import kr.co.yigil.comment.domain.CommentCount;
import kr.co.yigil.comment.domain.repository.CommentCountRepository;
import kr.co.yigil.comment.domain.repository.CommentRepository;
import kr.co.yigil.travel.domain.Travel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentRedisIntegrityService {
    private final CommentRepository commentRepository;
    private final CommentCountRepository commentCountRepository;

    @Transactional
    public CommentCount ensureCommentCount(Travel travel) {
        Long travelId = travel.getId();
        Optional<CommentCount> existingCommentCount = commentCountRepository.findByTravelId(travelId);
        if (existingCommentCount.isPresent()) {
            return existingCommentCount.get();
        } else {
            CommentCount commentCount = new CommentCount(
                travelId,
                commentRepository.countNonDeletedCommentsByTravelId(travelId)
            );
            return commentCountRepository.save(commentCount);
        }
    }

}
