package kr.co.yigil.follow.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import kr.co.yigil.follow.domain.FollowCount;
import kr.co.yigil.follow.domain.repository.FollowRepository;
import kr.co.yigil.follow.dto.FollowCountDto;
import kr.co.yigil.member.domain.Member;
import kr.co.yigil.member.domain.SocialLoginType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

public class FollowRedisIntegrityServiceTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private FollowRepository followRepository;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    private FollowRedisIntegrityService followRedisIntegrityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        followRedisIntegrityService = new FollowRedisIntegrityService(redisTemplate, followRepository);
    }

    @DisplayName("Redis에 FollowCount가 존재하지 않을 경우 DB에 조회 후 저장하는지")
    @Test
    void testEnsureFollowCountsWhenNotExist() {
        Member member = new Member(1L, "email", "12345678", "member", "image.jpg", SocialLoginType.KAKAO);
        FollowCountDto followCountDto = new FollowCountDto(10, 5);
        FollowCount followCount = new FollowCount(1L, 10, 5);

        when(redisTemplate.hasKey("followCount:1")).thenReturn(false);
        when(followRepository.getFollowCounts(member)).thenReturn(followCountDto);

        followRedisIntegrityService.ensureFollowCounts(member);

        verify(valueOperations).set(eq("followCount:1"), any(FollowCount.class));
    }

    @DisplayName("Redis에 FollowCount가 존재할 경우 DB 조회 및 저장 로직이 실행되지 않는지")
    @Test
    void testEnsureFollowCountsWhenExists() {
        Member member = new Member(1L, "email", "12345678", "member", "image.jpg", SocialLoginType.KAKAO);

        when(redisTemplate.hasKey("followCount:1")).thenReturn(true);

        followRedisIntegrityService.ensureFollowCounts(member);

        verify(valueOperations, never()).set(anyString(), any());
        verify(followRepository, never()).getFollowCounts(any(Member.class));
    }
}
