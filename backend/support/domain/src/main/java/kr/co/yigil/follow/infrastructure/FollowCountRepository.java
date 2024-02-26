package kr.co.yigil.follow.infrastructure;

import kr.co.yigil.follow.domain.FollowCount;
import org.springframework.data.repository.CrudRepository;

public interface FollowCountRepository extends CrudRepository<FollowCount, Long> {

}
