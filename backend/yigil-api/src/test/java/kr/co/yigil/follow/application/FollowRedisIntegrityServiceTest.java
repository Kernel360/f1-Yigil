package kr.co.yigil.follow.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import kr.co.yigil.follow.domain.FollowCount;
import kr.co.yigil.follow.domain.repository.FollowCountRepository;
import kr.co.yigil.follow.domain.repository.FollowRepository;
import kr.co.yigil.follow.dto.FollowCountDto;
import kr.co.yigil.member.domain.Member;
import kr.co.yigil.member.domain.SocialLoginType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class FollowRedisIntegrityServiceTest {

    @Mock
    private FollowRepository followRepository;

    @Mock
    private FollowCountRepository followCountRepository;

    @InjectMocks
    private FollowRedisIntegrityService followRedisIntegrityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("ensureFollowCounts 메서드가 이미 존재하는 FollowCount를 반환하는지")
    @Test
    void testEnsureFollowCountsWhenAlreadyExists() {
        // Given
        Member member = new Member(1L, "email", "12345678", "member", "image.jpg", SocialLoginType.KAKAO);
        FollowCount existingFollowCount = new FollowCount(1L, 10, 5);

        when(followCountRepository.findById(member.getId())).thenReturn(Optional.of(existingFollowCount));

        // When
        FollowCount result = followRedisIntegrityService.ensureFollowCounts(member);

        // Then
        assertThat(result).isEqualTo(existingFollowCount);
        verify(followRepository, never()).getFollowCounts(any());
        verify(followCountRepository, never()).save(any());
    }

    @DisplayName("ensureFollowCounts 메서드가 존재하지 않을 경우 FollowCount를 생성하고 저장하는지")
    @Test
    void testEnsureFollowCountsWhenNotExists() {
        // Given
        Member member = new Member(1L, "email", "12345678", "member", "image.jpg", SocialLoginType.KAKAO);
        FollowCountDto followCountDto = new FollowCountDto(10L, 5L);
        FollowCount newFollowCount = new FollowCount(1L, 10, 5);

        when(followCountRepository.findById(member.getId())).thenReturn(Optional.empty());
        when(followRepository.getFollowCounts(member)).thenReturn(followCountDto);
        when(followCountRepository.save(any())).thenReturn(newFollowCount);

        // When
        FollowCount result = followRedisIntegrityService.ensureFollowCounts(member);

        // Then
        assertThat(newFollowCount.getFollowerCount()).isEqualTo(result.getFollowerCount());
        assertThat(newFollowCount.getFollowingCount()).isEqualTo(result.getFollowingCount());
        verify(followRepository).getFollowCounts(member);
        verify(followCountRepository).save((FollowCount) any());
    }
}
