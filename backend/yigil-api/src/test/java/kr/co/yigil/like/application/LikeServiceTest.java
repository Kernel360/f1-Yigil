package kr.co.yigil.like.application;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.like.domain.Like;
import kr.co.yigil.like.domain.LikeCount;
import kr.co.yigil.like.domain.repository.LikeCountRepository;
import kr.co.yigil.like.domain.repository.LikeRepository;
import kr.co.yigil.member.domain.Member;
import kr.co.yigil.member.domain.SocialLoginType;
import kr.co.yigil.member.domain.repository.MemberRepository;
import kr.co.yigil.notification.application.NotificationService;
import kr.co.yigil.notification.domain.Notification;
import kr.co.yigil.post.domain.Post;
import kr.co.yigil.post.domain.repository.PostRepository;
import kr.co.yigil.travel.domain.Spot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class LikeServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private LikeCountRepository likeCountRepository;

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private NotificationService notificationService;

    @Mock
    private LikeRedisIntegrityService likeRedisIntegrityService;

    @InjectMocks
    private LikeService likeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Like 메서드가 좋아요를 잘 저장하고 알림을 잘 보내는지")
    @Test
    void whenLike_ShouldSaveLikeAndSendNotification() {
        Long memberId = 1L;
        Long postId = 1L;
        Member member = new Member(memberId, "email", "12345678", "member", "image.jpg", SocialLoginType.KAKAO);
        Spot spot = new Spot(1L);
        Post post = new Post(postId, spot, member);
        LikeCount likeCount = new LikeCount(postId, 1);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(likeCountRepository.findById(postId)).thenReturn(Optional.of(likeCount));
        when(likeRedisIntegrityService.ensureLikeCounts(post)).thenReturn(likeCount);

        likeService.like(memberId, postId);

        verify(likeRepository, times(1)).save(any(Like.class));
        verify(notificationService, times(1)).sendNotification(any(Notification.class));
    }

    @DisplayName("like 메서드가 존재하지 않는 게시글에 대한 요청을 보냈을 때 예외가 잘 발생하는지")
    @Test
    void whenLikeWithInvalidPostInfo_ShouldThrowException() {
        Long nonExisitingId = 3L;

        when(postRepository.findById(nonExisitingId)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> likeService.like(1L, nonExisitingId));
    }

    @DisplayName("unlike 메서드가 좋아요 정보를 잘 삭제하는지")
    @Test
    void whenUnlike_ShouldDeleteLike() {
        Long memberId = 1L;
        Long postId = 1L;
        Member member = new Member(memberId, "email", "12345678", "member", "image.jpg", SocialLoginType.KAKAO);
        Spot spot = new Spot(1L);
        Post post = new Post(postId, spot, member);
        LikeCount likeCount = new LikeCount(postId, 1);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(likeCountRepository.findById(postId)).thenReturn(Optional.of(likeCount));
        when(likeRedisIntegrityService.ensureLikeCounts(post)).thenReturn(likeCount);

        likeService.unlike(memberId, postId);

        verify(likeRepository, times(1)).deleteByMemberAndPost(any(Member.class), any(Post.class));
    }
}
