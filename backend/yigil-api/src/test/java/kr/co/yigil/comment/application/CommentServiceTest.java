package kr.co.yigil.comment.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import kr.co.yigil.comment.domain.Comment;
import kr.co.yigil.comment.domain.CommentCount;
import kr.co.yigil.comment.domain.repository.CommentCountRepository;
import kr.co.yigil.comment.domain.repository.CommentRepository;
import kr.co.yigil.comment.dto.request.CommentCreateRequest;
import kr.co.yigil.comment.dto.response.CommentCreateResponse;
import kr.co.yigil.comment.dto.response.CommentDeleteResponse;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.member.application.MemberService;
import kr.co.yigil.member.domain.Member;
import kr.co.yigil.member.domain.SocialLoginType;
import kr.co.yigil.notification.application.NotificationService;
import kr.co.yigil.notification.domain.Notification;
import kr.co.yigil.post.application.PostService;
import kr.co.yigil.post.domain.Post;
import kr.co.yigil.travel.domain.Spot;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private MemberService memberService;
    @Mock
    private PostService postService;
    @Mock
    private NotificationService notificationService;
    @Mock
    private CommentRedisIntegrityService commentRedisIntegrityService;
    @Mock
    private CommentCountRepository commentCountRepository;


    @DisplayName("createComment 메서드가 유효한 인자(부모 댓글이 없는 경우)를 넘겨받았을 때 올바른 응답을 내리는지.")
    @Test
    void whenCreateComment_thenReturnCommentCreateResponse() {

        Long memberId = 1L;
        Member mockMember = new Member("shin@gmail.com", "123456", "God", "profile.jpg", SocialLoginType.KAKAO);

        GeometryFactory geometryFactory = new GeometryFactory();
        Point mockPoint1 = geometryFactory.createPoint(new Coordinate(0,0));
        Spot mockSpot1 = new Spot(mockPoint1,"spot title1", "spot file url1","spot description1");
        Long postId = 1L;
        Post mockPost = new Post(postId, mockSpot1, mockMember);

        String content = "댓글 내용";
        CommentCreateRequest commentCreateRequest = new CommentCreateRequest(content, null, 2L);
        when(memberService.findMemberById(memberId)).thenReturn(mockMember);
        when(postService.findPostById(anyLong())).thenReturn(mockPost);

        int commentCount = 3;
        when(commentRedisIntegrityService.ensureCommentCount(mockPost)).thenReturn(new CommentCount(postId, commentCount));
        when(commentCountRepository.findByPostId(postId)).thenReturn(Optional.of(new CommentCount(postId, commentCount)));
        commentService.createComment(memberId, postId, commentCreateRequest);

        verify(commentRepository,times(1)).save(any(Comment.class));
        verify(commentRedisIntegrityService,times(1)).ensureCommentCount(mockPost);

        assertThat(commentCountRepository.findByPostId(postId).get().getCommentCount()).isEqualTo( commentCount + 1);
        assertThat(commentService.createComment(memberId, postId, commentCreateRequest)).isInstanceOf(
            CommentCreateResponse.class);
    }

    @DisplayName("createComment CommentRequest에 parentId가 있을 때 올바른 응답을 내리는지.")
    @Test
    void givenParentId_whenCreateComment_returnValidResponse() {
        Long memberId = 1L;
        Long notifiedMemberId = 2L;
        Long commentId = 3L;
        Member mockMember = new Member("shin@gmail.com", "123456", "God", "profile.jpg", SocialLoginType.KAKAO);

        GeometryFactory geometryFactory = new GeometryFactory();
        Point mockPoint1 = geometryFactory.createPoint(new Coordinate(0,0));
        Spot mockSpot1 = new Spot(mockPoint1,"spot title1", "spot file url1","spot description1");
        Long postId = 1L;
        Post mockPost = new Post(postId, mockSpot1, mockMember);

        String content = "댓글 내용";
        Long mockParentId = 1L;

        CommentCreateRequest commentCreateRequest = new CommentCreateRequest(content, mockParentId, notifiedMemberId);

        Member mockNotifiedMember = new Member("hoyoon@gmail.com", "1234567", "Alex", "hoyun.jpg", SocialLoginType.KAKAO);
        Comment mockComment = new Comment(commentId, content,mockMember, mockPost);

        Comment mockParentComment = new Comment(1L, "부모컨텐츠", mockNotifiedMember, mockPost);
        when(memberService.findMemberById(notifiedMemberId)).thenReturn(mockNotifiedMember);
        when(commentRepository.findById(mockParentId)).thenReturn(Optional.of(mockParentComment));

        when(memberService.findMemberById(memberId)).thenReturn(mockMember);
        when(postService.findPostById(anyLong())).thenReturn(mockPost);
        when(commentRepository.save(any(Comment.class))).thenReturn(mockComment);

        int commentCount = 3;
        when(commentCountRepository.findByPostId(postId)).thenReturn(Optional.of(new CommentCount(postId, commentCount)));

        commentService.createComment(memberId, postId, commentCreateRequest);
        verify(notificationService, times(1)).sendNotification(any(Notification.class));
        verify(commentRedisIntegrityService,times(1)).ensureCommentCount(mockPost);

        assertThat(commentCountRepository.findByPostId(postId).get().getCommentCount()).isEqualTo(commentCount + 1);
        assertThat(commentService.createComment(memberId, postId, commentCreateRequest)).isInstanceOf(
            CommentCreateResponse.class);
    }

    @DisplayName("getcommentList 메서드 실행 시 comment list 가 잘 반환되는지")
    @Test
    void whenGetCommentList_thenReturnCommentResponse() {
        Member mockMember = new Member("shin@gmail.com", "123456", "God", "profile.jpg", SocialLoginType.KAKAO);
        GeometryFactory geometryFactory = new GeometryFactory();
        Point mockPoint1 = geometryFactory.createPoint(new Coordinate(0,0));
        Spot mockSpot1 = new Spot(mockPoint1,"spot title1", "spot file url1","spot description1");
        Long postId = 1L;
        Post mockPost = new Post(postId, mockSpot1, mockMember);
        String content = "댓글 내용";

        Comment mockComment = new Comment(content, mockMember, mockPost);
        when(commentRepository.findCommentListByPostId(anyLong())).thenReturn(List.of(mockComment));

        assertThat(commentService.getCommentList(postId)).isInstanceOf(List.class);
    }

    @DisplayName("deleteComment 메서드가 유효한 인자를 받았을 때 comment 가 잘 삭제되는지")
    @Test
    void givenValidParameter_whenDeleteComment_thenReturnDeleteCommentResponse() {
        Member mockMember = new Member("shin@gmail.com", "123456", "God", "profile.jpg", SocialLoginType.KAKAO);
        GeometryFactory geometryFactory = new GeometryFactory();
        Point mockPoint1 = geometryFactory.createPoint(new Coordinate(0,0));
        Spot mockSpot1 = new Spot(mockPoint1,"spot title1", "spot file url1","spot description1");
        Long postId = 1L;
        Post mockPost = new Post(postId, mockSpot1, mockMember);
        when(postService.findPostById(anyLong())).thenReturn(mockPost);

        Long commentId = 1L;
        Long memberId = 1L;
        String content = "댓글 내용";
        Comment mockChildComment1 = new Comment(2L, "자식1", mockMember, mockPost);
        Comment mockComment = new Comment(commentId, content, mockMember, mockPost);
        mockChildComment1.setParent(mockComment);

        int commentCount = 3;
        CommentCount mockCommentCount = new CommentCount(postId, commentCount);
        when(commentRedisIntegrityService.ensureCommentCount(mockPost)).thenReturn(mockCommentCount);
        when(commentRepository.existsByMemberIdAndId(memberId, commentId)).thenReturn(true);
        when(commentRepository.findById(anyLong())).thenReturn(java.util.Optional.of(mockComment));
        when(commentCountRepository.findByPostId(anyLong())).thenReturn(Optional.of(mockCommentCount));

        CommentDeleteResponse commentDeleteResponse = commentService.deleteComment(memberId, postId, commentId);

        verify(commentRedisIntegrityService,times(1)).ensureCommentCount(mockPost);
        verify(commentRepository, times(1)).delete(any());
        
        assertThat(commentCountRepository.findByPostId(postId).get().getCommentCount()).isEqualTo(commentCount-2);
        assertEquals("댓글 삭제 성공", commentDeleteResponse.getMessage());
    }

    @DisplayName("findCommentById 메서드가 유효한 인자를 받았을 때 comment 가 잘 반환되는지")
    @Test
    void givenValidParameter_whenFindCommentById_thenReturnComment() {
        Long commentId = 1L;

        Member mockMember = new Member("shin@gmail.com", "123456", "God", "profile.jpg", SocialLoginType.KAKAO);

        GeometryFactory geometryFactory = new GeometryFactory();
        Point mockPoint1 = geometryFactory.createPoint(new Coordinate(0,0));
        Spot mockSpot1 = new Spot(mockPoint1,"spot title1", "spot file url1","spot description1");
        Long postId = 1L;
        Post mockPost = new Post(postId, mockSpot1, mockMember);

        Comment mockComment= new Comment(commentId, "content", mockMember, mockPost);

        when(commentRepository.findById(anyLong())).thenReturn(Optional.of(mockComment));

        // when
        Comment result = commentService.findCommentById(commentId);

        //then
        assertEquals(mockComment, result);

    }

    @DisplayName("validateCommentWriter 메서드 실행 시 comment 작성자와 로그인한 사용자가 같은지 확인")
    @Test
    void givenValidParameter_whenvalidateCommentWriter_thenReturnNothing() {
        Long memberId = 1L;
        Long commentId = 1L;
        when(commentRepository.existsByMemberIdAndId(memberId, commentId)).thenReturn(true);
        commentService.validateCommentWriter(memberId, commentId);
    }

    @DisplayName("validateCommentWriter 메서드 실행 시 comment 작성자와 로그인한 사용자가 다를 때 예외가 발생하는지")
    @Test
    public void testValidateCommentWriterWhenCommentDoesNotExist() {
        // 테스트에 사용될 memberId와 commentId 값 설정
        Long memberId = 1L;
        Long commentId = 2L;

        // commentRepository.existsByMemberIdAndId() 메서드의 반환값을 설정
        when(commentRepository.existsByMemberIdAndId(memberId, commentId)).thenReturn(false);

        // 테스트 대상 메서드 호출
        assertThrows(BadRequestException.class, () -> commentService.validateCommentWriter(memberId, commentId));

    }


}