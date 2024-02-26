package kr.co.yigil.follow.infrastructure;

import kr.co.yigil.follow.domain.FollowCacheReader;
import kr.co.yigil.follow.domain.FollowCount;
import kr.co.yigil.follow.domain.FollowReader;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FollowCacheReaderImpl implements FollowCacheReader {
    private final FollowReader followReader;

    @Override
    @Cacheable(value = "followCount")
    public FollowCount getFollowCount(Long memberId) {
        return followReader.getFollowCount(memberId);
    }
}
