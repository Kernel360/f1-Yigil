package kr.co.yigil.follow.domain.repository;


import java.util.Optional;
import kr.co.yigil.follow.domain.FollowCount;
import org.springframework.data.repository.CrudRepository;

public interface FollowCountRepository extends CrudRepository<FollowCount, Long> {

    Optional<FollowCount> findByMemberId(Long memberId);

}
