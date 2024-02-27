package kr.co.yigil.follow.domain;

public interface FollowService {

    void follow(Long followerId, Long followingId);

    void unfollow(Long unfollowerId, Long unfollowingId);
}
