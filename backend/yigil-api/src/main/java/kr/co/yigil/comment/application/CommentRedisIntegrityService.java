package kr.co.yigil.comment.application;

import java.util.Optional;
import kr.co.yigil.comment.domain.CommentCount;
import kr.co.yigil.comment.domain.repository.CommentCountRepository;
import kr.co.yigil.comment.domain.repository.CommentRepository;
import kr.co.yigil.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentRedisIntegrityService {
    private final CommentRepository commentRepository;
    private final CommentCountRepository commentCountRepository;

    @Transactional
    public CommentCount ensureCommentCount(Post post) {
        Long postId = post.getId();
        Optional<CommentCount> existingCommentCount = commentCountRepository.findByPostId(postId);
        if (existingCommentCount.isPresent()) {
            return existingCommentCount.get();
        } else {
            CommentCount commentCount = new CommentCount(
                postId,
                commentRepository.countByPostId(postId)
            );
            return commentCountRepository.save(commentCount);
        }
    }

}
