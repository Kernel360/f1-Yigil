package kr.co.yigil.comment.application;

import java.util.Optional;
import kr.co.yigil.comment.domain.CommentCount;
import kr.co.yigil.comment.domain.CommentCountRepository;
import kr.co.yigil.comment.domain.repository.CommentRepository;
import kr.co.yigil.travel.domain.Travel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentRedisIntegrityService {
    private final CommentRepository commentRepository;
    private final CommentCountRepository commentCountRepository;

    public CommentCount ensureCommentCount(Travel travel) {
        Optional<CommentCount> existingCommentCount = commentCountRepository.findByTravelId(travel.getId());
        if (existingCommentCount.isPresent()) {
            return existingCommentCount.get();
        } else {
            CommentCount commentCount = new CommentCount(
                    travel.getId(),
                    commentRepository.countByTravelId(travel.getId())
            );
            return commentCountRepository.save(commentCount);
        }
    }

}
