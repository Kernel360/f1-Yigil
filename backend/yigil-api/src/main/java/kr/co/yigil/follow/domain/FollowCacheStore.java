package kr.co.yigil.follow.domain;

public interface FollowCacheStore {

    FollowCount incrementFollowingsCount(Long memberId);

    FollowCount decrementFollowingsCount(Long memberId);

    FollowCount incrementFollowersCount(Long memberId);

    FollowCount decrementFollowersCount(Long memberId);

}