package kr.co.yigil.follow.domain;

import jakarta.transaction.Transactional;
import kr.co.yigil.member.Member;

public interface FollowStore {

    @Transactional
    void store(Member follower, Member following);

    @Transactional
    void remove(Member unfollower, Member unfollowing);
}
