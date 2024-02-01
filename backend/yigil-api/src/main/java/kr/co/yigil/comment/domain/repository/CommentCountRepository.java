package kr.co.yigil.comment.domain.repository;

import java.util.Optional;
import kr.co.yigil.comment.domain.CommentCount;
import org.springframework.data.repository.CrudRepository;

public interface CommentCountRepository extends CrudRepository<CommentCount, Long> {

    Optional<CommentCount> findByTravelId(Long travelId);
}
