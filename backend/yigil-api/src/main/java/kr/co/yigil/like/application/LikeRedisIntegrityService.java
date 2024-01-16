package kr.co.yigil.like.application;

import jakarta.transaction.Transactional;
import java.util.Optional;
import kr.co.yigil.like.domain.LikeCount;
import kr.co.yigil.like.domain.repository.LikeCountRepository;
import kr.co.yigil.like.domain.repository.LikeRepository;
import kr.co.yigil.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeRedisIntegrityService {
    private final LikeRepository likeRepository;
    private final LikeCountRepository likeCountRepository;

    @Transactional
    public LikeCount ensureLikeCounts(Post post) {
        Long postId = post.getId();
        Optional<LikeCount> existingLikeCount = likeCountRepository.findById(postId);
        if (existingLikeCount.isPresent()) {
            return existingLikeCount.get();
        } else {
            LikeCount likeCount = getLikeCount(post);
            likeCountRepository.save(likeCount);
            return likeCount;
        }
    }

    private LikeCount getLikeCount(Post post) {
        return new LikeCount(post.getId(), likeRepository.countByPostId(post.getId()));
    }
}
