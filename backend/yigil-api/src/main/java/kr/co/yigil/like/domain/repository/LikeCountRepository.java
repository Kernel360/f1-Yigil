package kr.co.yigil.like.domain.repository;

import kr.co.yigil.like.domain.LikeCount;
import org.springframework.data.repository.CrudRepository;

public interface LikeCountRepository extends CrudRepository<LikeCount, Long> {

}
