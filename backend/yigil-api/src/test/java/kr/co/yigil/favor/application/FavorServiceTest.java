package kr.co.yigil.favor.application;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.favor.domain.Favor;
import kr.co.yigil.favor.domain.FavorCount;
import kr.co.yigil.favor.domain.repository.FavorCountRepository;
import kr.co.yigil.favor.domain.repository.FavorRepository;
import kr.co.yigil.member.domain.Member;
import kr.co.yigil.member.domain.SocialLoginType;
import kr.co.yigil.member.domain.repository.MemberRepository;
import kr.co.yigil.notification.application.NotificationService;
import kr.co.yigil.notification.domain.Notification;
import kr.co.yigil.post.domain.Post;
import kr.co.yigil.post.domain.repository.PostRepository;
import kr.co.yigil.travel.Spot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class FavorServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private FavorCountRepository favorCountRepository;

    @Mock
    private FavorRepository favorRepository;

    @Mock
    private NotificationService notificationService;

    @Mock
    private FavorRedisIntegrityService favorRedisIntegrityService;

    @InjectMocks
    private FavorService favorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("addFavor 메서드가 좋아요를 잘 저장하고 알림을 잘 보내는지")
    @Test
    void whenAddFavor_ShouldSaveFavorAndSendNotification() {
        Long memberId = 1L;
        Long postId = 1L;
        Member member = new Member(memberId, "email", "12345678", "member", "image.jpg", SocialLoginType.KAKAO);
        Spot spot = new Spot(1L);
        Post post = new Post(postId, spot, member);
        FavorCount favorCount = new FavorCount(postId, 1);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(favorCountRepository.findById(postId)).thenReturn(Optional.of(favorCount));
        when(favorRedisIntegrityService.ensureFavorCounts(post)).thenReturn(favorCount);

        favorService.addFavor(memberId, postId);

        verify(favorRepository, times(1)).save(any(Favor.class));
        verify(notificationService, times(1)).sendNotification(any(Notification.class));
    }

    @DisplayName("addFavor 메서드가 존재하지 않는 게시글에 대한 요청을 보냈을 때 예외가 잘 발생하는지")
    @Test
    void whenAddFavorWithInvalidPostInfo_ShouldThrowException() {
        Long nonExisitingId = 3L;

        when(postRepository.findById(nonExisitingId)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> favorService.addFavor(1L, nonExisitingId));
    }

    @DisplayName("deleteFavor 메서드가 좋아요 정보를 잘 삭제하는지")
    @Test
    void whenDeleteFavor_ShouldDeleteFavor() {
        Long memberId = 1L;
        Long postId = 1L;
        Member member = new Member(memberId, "email", "12345678", "member", "image.jpg", SocialLoginType.KAKAO);
        Spot spot = new Spot(1L);
        Post post = new Post(postId, spot, member);
        FavorCount favorCount = new FavorCount(postId, 1);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(favorCountRepository.findById(postId)).thenReturn(Optional.of(favorCount));
        when(favorRedisIntegrityService.ensureFavorCounts(post)).thenReturn(favorCount);

        favorService.deleteFavor(memberId, postId);

        verify(favorRepository, times(1)).deleteByMemberAndPost(any(Member.class), any(Post.class));
    }
}
