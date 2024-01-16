package kr.co.yigil.like.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import kr.co.yigil.like.domain.LikeCount;
import kr.co.yigil.like.domain.repository.LikeCountRepository;
import kr.co.yigil.like.domain.repository.LikeRepository;
import kr.co.yigil.member.domain.Member;
import kr.co.yigil.member.domain.SocialLoginType;
import kr.co.yigil.post.domain.Post;
import kr.co.yigil.travel.domain.Spot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class LikeRedisIntegrityServiceTest {

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private LikeCountRepository likeCountRepository;

    @InjectMocks
    private LikeRedisIntegrityService likeRedisIntegrityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("ensureLikeCounts 메서드가 이미 존재하는 LikeCount를 반환하는지")
    @Test
    void testEnsureLikeCountsWhenAlreadyExists() {
        Member member = new Member(1L, "email", "12345678", "member", "image.jpg", SocialLoginType.KAKAO);
        Spot spot = new Spot(1L);
        Post post = new Post(1L, spot, member);
        LikeCount existingLikeCount = new LikeCount(1L, 10);

        when(likeCountRepository.findById(post.getId())).thenReturn(Optional.of(existingLikeCount));

        LikeCount result = likeRedisIntegrityService.ensureLikeCounts(post);

        assertThat(result).isEqualTo(existingLikeCount);
        verify(likeRepository, never()).countByPostId(post.getId());
        verify(likeCountRepository, never()).save(any());
    }

    @DisplayName("ensureLikeCounts 메서드가 존재하지 않을 경우 LikeCount를 생성하고 저장하는지")
    @Test
    void testEnsureLikeCountsWhenNotExists() {
        Member member = new Member(1L, "email", "12345678", "member", "image.jpg", SocialLoginType.KAKAO);
        Spot spot = new Spot(1L);
        Post post = new Post(1L, spot, member);
        LikeCount newLikeCount = new LikeCount(1L, 10);

        when(likeCountRepository.findById(post.getId())).thenReturn(Optional.empty());
        when(likeRepository.countByPostId(post.getId())).thenReturn(10);
        when(likeCountRepository.save(any())).thenReturn(newLikeCount);

        LikeCount count = likeRedisIntegrityService.ensureLikeCounts(post);

        assertThat(count.getLikeCount()).isEqualTo(newLikeCount.getLikeCount());
        verify(likeRepository).countByPostId(post.getId());
        verify(likeCountRepository).save(any());
    }
}
