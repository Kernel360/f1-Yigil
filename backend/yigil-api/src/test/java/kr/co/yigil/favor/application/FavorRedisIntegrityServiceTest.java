//package kr.co.yigil.favor.application;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.never;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.util.Optional;
//import kr.co.yigil.favor.domain.FavorCount;
//import kr.co.yigil.favor.domain.repository.FavorCountRepository;
//import kr.co.yigil.favor.domain.repository.FavorRepository;
//import kr.co.yigil.member.Member;
//import kr.co.yigil.member.SocialLoginType;
//import kr.co.yigil.post.domain.Post;
//import kr.co.yigil.travel.Spot;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//public class FavorRedisIntegrityServiceTest {
//
//    @Mock
//    private FavorRepository favorRepository;
//
//    @Mock
//    private FavorCountRepository favorCountRepository;
//
//    @InjectMocks
//    private FavorRedisIntegrityService favorRedisIntegrityService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @DisplayName("ensureFavorCounts 메서드가 이미 존재하는 FavorCount를 잘 반환하는지")
//    @Test
//    void testEnsureLikeCountsWhenAlreadyExists() {
//        Member member = new Member(1L, "email", "12345678", "member", "image.jpg", SocialLoginType.KAKAO);
//        Spot spot = new Spot(1L);
//        Post post = new Post(1L, spot, member);
//        FavorCount existingFavorCount = new FavorCount(1L, 10);
//
//        when(favorCountRepository.findById(post.getId())).thenReturn(Optional.of(existingFavorCount));
//
//        FavorCount result = favorRedisIntegrityService.ensureFavorCounts(post);
//
//        assertThat(result).isEqualTo(existingFavorCount);
//        verify(favorRepository, never()).countByPostId(post.getId());
//        verify(favorCountRepository, never()).save(any());
//    }
//
//    @DisplayName("ensureLikeCounts 메서드가 존재하지 않을 경우 LikeCount를 생성하고 저장하는지")
//    @Test
//    void testEnsureLikeCountsWhenNotExists() {
//        Member member = new Member(1L, "email", "12345678", "member", "image.jpg", SocialLoginType.KAKAO);
//        Spot spot = new Spot(1L);
//        Post post = new Post(1L, spot, member);
//        FavorCount newFavorCount = new FavorCount(1L, 10);
//
//        when(favorCountRepository.findById(post.getId())).thenReturn(Optional.empty());
//        when(favorRepository.countByPostId(post.getId())).thenReturn(10);
//        when(favorCountRepository.save(any())).thenReturn(newFavorCount);
//
//        FavorCount count = favorRedisIntegrityService.ensureFavorCounts(post);
//
//        assertThat(count.getFavorCount()).isEqualTo(newFavorCount.getFavorCount());
//        verify(favorRepository).countByPostId(post.getId());
//        verify(favorCountRepository).save(any());
//    }
//}
