package kr.co.yigil.follow.domain;

import org.springframework.cache.annotation.Cacheable;

public interface FollowCacheReader {
    FollowCount getFollowCount(Long memberId);
}
