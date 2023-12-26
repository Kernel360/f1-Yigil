package kr.co.yigil.follow.domain.repository;

import kr.co.yigil.follow.domain.Follow;
import kr.co.yigil.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    public void deleteByFollowerAndFollowing(Member Follower, Member Following);
}
