package kr.co.yigil.follow.infrastructure;

import kr.co.yigil.follow.domain.FollowCacheReader;
import kr.co.yigil.follow.domain.FollowCacheStore;
import kr.co.yigil.follow.domain.FollowCount;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FollowCacheStoreImpl implements FollowCacheStore {
    private final FollowCacheReader followCacheReader;

    @Override
    @CachePut(value = "followCount")
    public FollowCount incrementFollowingsCount(Long memberId) {
        FollowCount followCount = followCacheReader.getFollowCount(memberId);
        followCount.incrementFollowingCount();
        return followCount;
    }

    @Override
    @CachePut(value = "followCount")
    public FollowCount decrementFollowingsCount(Long memberId) {
        FollowCount followCount = followCacheReader.getFollowCount(memberId);
        followCount.decrementFollowingCount();
        return followCount;
    }

    @Override
    @CachePut(value = "followCount")
    public FollowCount incrementFollowersCount(Long memberId) {
        FollowCount followCount = followCacheReader.getFollowCount(memberId);
        followCount.incrementFollowerCount();
        return followCount;
    }

    @Override
    @CachePut(value = "followCount")
    public FollowCount decrementFollowersCount(Long memberId) {
        FollowCount followCount = followCacheReader.getFollowCount(memberId);
        followCount.decrementFollowerCount();
        return followCount;
    }
}
