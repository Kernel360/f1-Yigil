package kr.co.yigil.follow.infrastructure;

import kr.co.yigil.follow.domain.Follow;
import kr.co.yigil.follow.FollowCountDto;
import kr.co.yigil.member.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    public boolean existsByFollowerIdAndFollowingId(Long followerId, Long followingId);

    public Slice<Follow> findAllByFollowing(Member member, Pageable pageable);

    public Slice<Follow> findAllByFollower(Member member, Pageable pageable);

    @Query("SELECT new kr.co.yigil.follow.FollowCountDto(" +
        "   (SELECT COUNT(f1) FROM Follow f1 WHERE f1.following = :member), " +
        "   (SELECT COUNT(f2) FROM Follow f2 WHERE f2.follower = :member))")
    FollowCountDto getFollowCounts(@Param("member") Member member);

    public void deleteByFollowerAndFollowing(Member Follower, Member Following);
}
