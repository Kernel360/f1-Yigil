package kr.co.yigil.like.domain.repository;

import java.util.Optional;
import kr.co.yigil.like.domain.LikeCount;
import org.springframework.data.repository.CrudRepository;

public interface LikeCountRepository extends CrudRepository<LikeCount, Long> {

    Optional<LikeCount> findByPostId(Long postId);
}
