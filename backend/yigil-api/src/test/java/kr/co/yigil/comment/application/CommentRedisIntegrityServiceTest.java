package kr.co.yigil.comment.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import kr.co.yigil.comment.domain.CommentCount;
import kr.co.yigil.comment.domain.repository.CommentCountRepository;
import kr.co.yigil.comment.domain.repository.CommentRepository;
import kr.co.yigil.member.domain.Member;
import kr.co.yigil.member.domain.SocialLoginType;
import kr.co.yigil.post.domain.Post;
import kr.co.yigil.travel.domain.Spot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CommentRedisIntegrityServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CommentCountRepository commentCountRepository;

    @InjectMocks
    private CommentRedisIntegrityService commentRedisIntegrityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("ensureCommentCount 메서드가 이미 존재하는 CommentCount를 반환하는지")
    @Test
    void testEnsureCommentCountWhenAlreadyExists() {

        Long memberId = 1L;
        Member mockMember = new Member(memberId, "shin@gmail.com", "123456", "똷", "profile.jpg", SocialLoginType.KAKAO);

        GeometryFactory geometryFactory = new GeometryFactory();
        Point mockPoint1 = geometryFactory.createPoint(new Coordinate(0,0));
        Spot mockSpot1 = new Spot(mockPoint1,"spot title1", "spot file url1","spot description1");
        Long postId = 1L;
        Post post = new Post(postId, mockSpot1, mockMember);
        CommentCount existingCommentCount = new CommentCount(1L, 5);

        when(commentCountRepository.findByPostId(postId)).thenReturn(Optional.of(existingCommentCount));

        CommentCount result = commentRedisIntegrityService.ensureCommentCount(post);

        assertThat(result).isEqualTo(existingCommentCount);
        verify(commentRepository, never()).countNonDeletedCommentsByPostId(post.getId());
        verify(commentCountRepository, never()).save(any());
    }

    @DisplayName("ensureCommentCount 메서드가 존재하지 않을 경우 CommentCount를 생성하고 저장하는지")
    @Test
    void testEnsureCommentCountWhenNotExists() {
        Long memberId = 1L;
        Member mockMember = new Member(memberId, "shin@gmail.com", "123456", "God", "profile.jpg", SocialLoginType.KAKAO);

        GeometryFactory geometryFactory = new GeometryFactory();
        Point mockPoint1 = geometryFactory.createPoint(new Coordinate(0,0));
        Spot mockSpot1 = new Spot(mockPoint1,"spot title1", "spot file url1","spot description1");
        Long postId = 1L;
        Post post = new Post(postId, mockSpot1, mockMember);

        CommentCount newCommentCount = new CommentCount(1L, 10);

        when(commentCountRepository.findByPostId(post.getId())).thenReturn(Optional.empty());
        when(commentRepository.countNonDeletedCommentsByPostId(post.getId())).thenReturn(10);
        when(commentCountRepository.save(any())).thenReturn(newCommentCount);

        CommentCount count = commentRedisIntegrityService.ensureCommentCount(post);

        assertThat(count.getCommentCount()).isEqualTo(newCommentCount.getCommentCount());
        verify(commentRepository).countNonDeletedCommentsByPostId(post.getId());
        verify(commentCountRepository).save(any());
    }
}
