package kr.co.yigil.follow.infrastructure;

import kr.co.yigil.follow.domain.FollowCacheReader;
import kr.co.yigil.follow.domain.FollowCount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FollowCacheStoreImplTest {

    @MockBean
    private FollowCacheReader followCacheReader;

    @Autowired
    private FollowCacheStoreImpl followCacheStore;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    public void clearCache() {
        cacheManager.getCache("followCount").clear();
    }

    @BeforeEach
    public void setUp() {
        Long memberId = 1L;
        FollowCount mockFollowCount = new FollowCount(memberId, 10, 20);
        when(followCacheReader.getFollowCount(memberId)).thenReturn(mockFollowCount);
    }

    @Test
    void whenIncrementFollowingsCount_thenValueIsStoredInCache() {
        Long memberId = 1L;
        FollowCount expectedFollowCount = new FollowCount(memberId, 10, 21);

        followCacheStore.incrementFollowingsCount(memberId);

        Cache followCountCache = cacheManager.getCache("followCount");
        FollowCount cachedFollowCount = followCountCache.get(memberId, FollowCount.class);

        assertEquals(expectedFollowCount, cachedFollowCount);
    }

    @Test
    void whenDecrementFollowingsCount_thenValueIsStoredInCache() {
        Long memberId = 1L;
        FollowCount expectedFollowCount = new FollowCount(memberId, 10, 19);

        followCacheStore.decrementFollowingsCount(memberId);

        Cache followCountCache = cacheManager.getCache("followCount");
        FollowCount cachedFollowCount = followCountCache.get(memberId, FollowCount.class);

        assertEquals(expectedFollowCount, cachedFollowCount);
    }

    @Test
    void whenIncrementFollowersCount_thenValueIsStoredInCache() {
        Long memberId = 1L;
        FollowCount expectedFollowCount = new FollowCount(memberId, 11, 20);

        followCacheStore.incrementFollowersCount(memberId);

        Cache followCountCache = cacheManager.getCache("followCount");
        FollowCount cachedFollowCount = followCountCache.get(memberId, FollowCount.class);

        assertEquals(expectedFollowCount, cachedFollowCount);
    }

    @Test
    void whenDecrementFollowersCount_thenValueIsStoredInCache() {
        Long memberId = 1L;
        FollowCount expectedFollowCount = new FollowCount(memberId, 9, 20);

        followCacheStore.decrementFollowersCount(memberId);

        Cache followCountCache = cacheManager.getCache("followCount");
        FollowCount cachedFollowCount = followCountCache.get(memberId, FollowCount.class);

        assertEquals(expectedFollowCount, cachedFollowCount);
    }

}