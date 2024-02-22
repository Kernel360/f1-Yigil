package kr.co.yigil.follow.domain;

public interface FollowReader {

    FollowCount getFollowCount(Long memberId);

    boolean isFollowing(Long followerId, Long followingId);
}
