package kr.co.yigil.follow.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface FollowReader {

    FollowCount getFollowCount(Long memberId);

    boolean isFollowing(Long followerId, Long followingId);

    Slice<Follow> getFollowerSlice(Long memberId, Pageable pageable);

    Slice<Follow> getFollowingSlice(Long memberId, Pageable pageable);
}
