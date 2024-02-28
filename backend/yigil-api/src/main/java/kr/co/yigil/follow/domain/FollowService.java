package kr.co.yigil.follow.domain;

import kr.co.yigil.follow.domain.FollowInfo.FollowingsResponse;
import org.springframework.data.domain.Pageable;

public interface FollowService {

    void follow(Long followerId, Long followingId);

    void unfollow(Long unfollowerId, Long unfollowingId);

    FollowInfo.FollowersResponse getFollowerList(Long memberId, Pageable pageable);

    FollowingsResponse getFollowingList(Long memberId, Pageable pageable);
}
