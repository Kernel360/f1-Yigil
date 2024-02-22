package kr.co.yigil.follow.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import kr.co.yigil.follow.domain.FollowCount;
import kr.co.yigil.follow.domain.FollowReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootTest
@EnableCaching
public class FollowCacheReaderImplTest {

    @MockBean
    private FollowReader followReader;

    @Autowired
    private FollowCacheReaderImpl followCacheReaderImpl;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    public void clearCache() {
        cacheManager.getCache("followCount").clear();
    }

    @DisplayName("getFollowCount 메서드가 캐시를 올바르게 사용하는지")
    @Test
    void whenGetFollowCount_thenUsesCache() {
        Long memberId = 1L;
        FollowCount mockFollowCount = new FollowCount(memberId, 20, 30);

        when(followReader.getFollowCount(memberId)).thenReturn(mockFollowCount);

        // 첫 번째 호출에서는 FollowReader의 getFollowCount 메소드가 호출됩니다.
        FollowCount followCount1 = followCacheReaderImpl.getFollowCount(memberId);
        assertEquals(mockFollowCount.getMemberId(), followCount1.getMemberId());

        // 두 번째 호출에서는 캐시된 결과를 반환하므로 FollowReader의 getFollowCount 메소드가 호출되지 않습니다.
        FollowCount followCount2 = followCacheReaderImpl.getFollowCount(memberId);
        assertEquals(mockFollowCount.getMemberId(), followCount2.getMemberId());

        verify(followReader, times(1)).getFollowCount(memberId);
    }
}