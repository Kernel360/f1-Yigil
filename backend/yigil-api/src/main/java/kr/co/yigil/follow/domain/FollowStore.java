package kr.co.yigil.follow.domain;

import jakarta.transaction.Transactional;
import kr.co.yigil.member.Member;

public interface FollowStore {

    @Transactional
    void follow(Member follower, Member following);

    @Transactional
    void unfollow(Member unfollower, Member unfollowing);
}
