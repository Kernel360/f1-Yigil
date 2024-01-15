package kr.co.yigil.comment.domain;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface CommentCountRepository extends CrudRepository<CommentCount, Long> {

    Optional<CommentCount> findByTravelId(Long id);
}
