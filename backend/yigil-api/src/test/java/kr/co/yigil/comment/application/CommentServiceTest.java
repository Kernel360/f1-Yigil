package kr.co.yigil.comment.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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
import kr.co.yigil.comment.dto.response.CommentResponse;
import kr.co.yigil.file.AttachFile;
import kr.co.yigil.file.AttachFiles;
import kr.co.yigil.file.FileType;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.SocialLoginType;
import kr.co.yigil.member.application.MemberService;
import kr.co.yigil.notification.application.NotificationService;
import kr.co.yigil.notification.domain.Notification;
import kr.co.yigil.place.Place;
import kr.co.yigil.travel.Spot;
import kr.co.yigil.travel.application.TravelService;
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
    private NotificationService notificationService;
    @Mock
    private CommentRedisIntegrityService commentRedisIntegrityService;
    @Mock
    private CommentCountRepository commentCountRepository;
    @Mock
    private TravelService travelService;


    @DisplayName("createComment 메서드가 유효한 인자(부모 댓글이 없는 경우)를 넘겨받았을 때 올바른 응답을 내리는지.")
    @Test
    void whenCreateComment_thenReturnCommentCreateResponse() {

        Long memberId = 1L;
        Member mockMember = new Member("shin@gmail.com", "123456", "똷", "profile.jpg",
                SocialLoginType.KAKAO);

        GeometryFactory geometryFactory = new GeometryFactory();
        Point mockPoint = geometryFactory.createPoint(new Coordinate(0, 0));
        Place mockPlace = new Place("anyName", "anyImageUrl", mockPoint, "anyDescription");
        AttachFile mockAttachFile1 = new AttachFile(FileType.IMAGE, "fileUrl1", "originalFileName1",
                1L);
        AttachFile mockAttachFile2 = new AttachFile(FileType.IMAGE, "fileUrl2", "originalFileName2",
                2L);
        AttachFiles mockAttachFiles = new AttachFiles(List.of(mockAttachFile1, mockAttachFile2));

        Spot mockSpot = new Spot(1L, mockMember, mockPoint, false, "anyTitle", "아무말",
                mockAttachFiles,mockAttachFile1,
                mockPlace, 5.0);

        CommentCreateRequest commentCreateRequest = new CommentCreateRequest("댓글 내용", null, null);

        when(memberService.findMemberById(memberId)).thenReturn(mockMember);

        Long travelId = 1L;
        when(travelService.findTravelById(travelId)).thenReturn(mockSpot);

        CommentCount mockCommentCount = new CommentCount(travelId, 1);
        when(commentRedisIntegrityService.ensureCommentCount(any(Spot.class))).thenReturn(
                mockCommentCount);

        commentService.createComment(memberId, travelId, commentCreateRequest);

        verify(commentRedisIntegrityService, times(1)).ensureCommentCount(mockSpot);
        verify(commentRepository, times(1)).save(any(Comment.class));
        assertThat(commentService.createComment(memberId, travelId,
                commentCreateRequest)).isInstanceOf(
                CommentCreateResponse.class);

    }

    @DisplayName("createComment CommentRequest에 parentId가 있을 때 올바른 응답을 내리는지.")
    @Test
    void givenParentId_whenCreateComment_returnValidResponse() {
        Long memberId = 1L;
        Long notifiedMemberId = 2L;
        Long travelId = 1L;

        Member mockMember = new Member("shin@gmail.com", "123456", "God", "profile.jpg",
                SocialLoginType.KAKAO);

        GeometryFactory geometryFactory = new GeometryFactory();
        Point mockPoint = geometryFactory.createPoint(new Coordinate(0, 0));
        Place mockPlace = new Place("anyName", "anyImageUrl", mockPoint, "anyDescription");
        AttachFile mockAttachFile1 = new AttachFile(FileType.IMAGE, "fileUrl1", "originalFileName1",
                1L);
        AttachFile mockAttachFile2 = new AttachFile(FileType.IMAGE, "fileUrl2", "originalFileName2",
                2L);
        AttachFiles mockAttachFiles = new AttachFiles(List.of(mockAttachFile1, mockAttachFile2));

        Spot mockSpot = new Spot(travelId, mockMember, mockPoint, false, "anyTitle", "아무말",
                mockAttachFiles, mockAttachFile1,
                mockPlace, 5.0);

        Long commentParentId = 3L;

        CommentCreateRequest commentCreateRequest = new CommentCreateRequest("댓글 내용",
                commentParentId, notifiedMemberId);

        Member mockNotifiedMember = new Member(notifiedMemberId, "hoyoon@gmail.com", "1234567",
                "Alex", "hoyun.jpg", SocialLoginType.KAKAO);

        Comment mockParentComment = new Comment(commentParentId, "부모컨텐츠", mockNotifiedMember,
                mockSpot);
        Comment mockComment = new Comment(commentParentId, "content", mockMember, mockSpot,
                mockParentComment);

        when(memberService.findMemberById(notifiedMemberId)).thenReturn(mockNotifiedMember);
        when(commentRepository.findById(commentParentId)).thenReturn(
                Optional.of(mockParentComment));

        when(memberService.findMemberById(memberId)).thenReturn(mockMember);
        when(travelService.findTravelById(anyLong())).thenReturn(mockSpot);
        when(commentRepository.save(any(Comment.class))).thenReturn(mockComment);

        int commentCount = 3;
        when(commentRedisIntegrityService.ensureCommentCount(mockSpot)).thenReturn(
                new CommentCount(travelId, commentCount));

        commentService.createComment(memberId, travelId, commentCreateRequest);
        verify(notificationService, times(1)).sendNotification(any(Notification.class));

        assertThat(commentRedisIntegrityService.ensureCommentCount(mockSpot)
                .getCommentCount()).isEqualTo(commentCount + 1);
        assertThat(commentService.createComment(memberId, travelId,
                commentCreateRequest)).isInstanceOf(
                CommentCreateResponse.class);
    }

    @DisplayName("getcommentList 메서드 실행 시 comment list 가 잘 반환되는지")
    @Test
    void whenGetCommentList_thenReturnCommentResponse() {
        Long travelId = 1L;
        Long parentCommentId = 1L;

        Long memberId = 1L;
        Member mockMember = new Member(memberId, "shin@gmail.com", "123456", "똷", "profile.jpg",
                SocialLoginType.KAKAO);

        GeometryFactory geometryFactory = new GeometryFactory();
        Point mockPoint = geometryFactory.createPoint(new Coordinate(0, 0));
        Place mockPlace = new Place("anyName", "anyImageUrl", mockPoint, "anyDescription");
        AttachFile mockAttachFile1 = new AttachFile(FileType.IMAGE, "fileUrl1", "originalFileName1",
                1L);
        AttachFile mockAttachFile2 = new AttachFile(FileType.IMAGE, "fileUrl2", "originalFileName2",
                2L);
        AttachFiles mockAttachFiles = new AttachFiles(List.of(mockAttachFile1, mockAttachFile2));

        Spot mockSpot = new Spot(travelId, mockMember, mockPoint, false, "anyTitle", "아무말",
                mockAttachFiles,mockAttachFile1,
                mockPlace, 5.0);

        Comment mockParentComment = new Comment(parentCommentId, "content", mockMember, mockSpot);
        Comment mockComment1 = new Comment(2L, "content", mockMember, mockSpot, mockParentComment);
        Comment mockComment2 = new Comment(3L, "content", mockMember, mockSpot, mockParentComment);

        when(commentRepository.findParentCommentsByTravelId(anyLong())).thenReturn(
                List.of(mockParentComment));
        when(commentRepository.findChildCommentsByTravelIdAndParentId(anyLong(), anyLong())).thenReturn(
                List.of(mockComment1, mockComment2));

        assertThat(commentService.getCommentList(travelId)).isInstanceOf(List.class);
        assertThat(commentService.getCommentList(travelId).get(0)).isInstanceOf(
                CommentResponse.class);
        assert (commentService.getCommentList(travelId).size() == 1);
    }

    @DisplayName("deleteComment 메서드가 유효한 인자를 받았을 때 comment 가 잘 삭제되는지")
    @Test
    void givenValidParameter_whenDeleteComment_thenReturnDeleteCommentResponse() {
        Long travelId = 1L;
        Long commentId = 2L;
        Long memberId = 3L;

        Member mockMember = new Member(memberId, "shin@gmail.com", "123456", "똷", "profile.jpg",
                SocialLoginType.KAKAO);

        GeometryFactory geometryFactory = new GeometryFactory();
        Point mockPoint = geometryFactory.createPoint(new Coordinate(0, 0));
        Place mockPlace = new Place("anyName", "anyImageUrl", mockPoint, "anyDescription");
        AttachFile mockAttachFile1 = new AttachFile(FileType.IMAGE, "fileUrl1", "originalFileName1",
                1L);
        AttachFile mockAttachFile2 = new AttachFile(FileType.IMAGE, "fileUrl2", "originalFileName2",
                2L);
        AttachFiles mockAttachFiles = new AttachFiles(List.of(mockAttachFile1, mockAttachFile2));

        Spot mockSpot = new Spot(travelId, mockMember, mockPoint, false, "anyTitle", "아무말",
                mockAttachFiles,mockAttachFile1,
                mockPlace, 5.0);

        int commentCount = 1;
        CommentCount mockCommentCount = new CommentCount(travelId, commentCount);
        when(travelService.findTravelById(travelId)).thenReturn(mockSpot);
        when(commentRedisIntegrityService.ensureCommentCount(any(Spot.class))).thenReturn(
                mockCommentCount);

        Comment mockComment = new Comment(commentId, "content", mockMember, mockSpot);
        when(commentRepository.findByIdAndMemberId(commentId, memberId)).thenReturn(Optional.of(mockComment));

        CommentDeleteResponse commentDeleteResponse = commentService.deleteComment(memberId,
                travelId, commentId);

        verify(commentRepository, times(1)).delete(any());
        assertThat(commentRedisIntegrityService.ensureCommentCount(mockSpot)
                .getCommentCount()).isEqualTo(0);
        assertEquals("댓글 삭제 성공", commentDeleteResponse.getMessage());
    }



    @DisplayName("getTopLevelCommentList 메서드가 유효한 인자를 받았을 때 comment list 가 잘 반환되는지")
    @Test
    void givenValidParameter_whenGetTopLevelCommentList_thenReturnCommentResponse() {
        Long travelId = 1L;
        Long parentCommentId = 1L;

        Long memberId = 1L;
        Member mockMember = new Member(memberId, "shin@gmail.com", "123456", "똷", "profile.jpg",
                SocialLoginType.KAKAO);

        GeometryFactory geometryFactory = new GeometryFactory();
        Point mockPoint = geometryFactory.createPoint(new Coordinate(0, 0));
        Place mockPlace = new Place("anyName", "anyImageUrl", mockPoint, "anyDescription");
        AttachFile mockAttachFile1 = new AttachFile(FileType.IMAGE, "fileUrl1", "originalFileName1",
                1L);
        AttachFile mockAttachFile2 = new AttachFile(FileType.IMAGE, "fileUrl2", "originalFileName2",
                2L);
        AttachFiles mockAttachFiles = new AttachFiles(List.of(mockAttachFile1, mockAttachFile2));

        Spot mockSpot = new Spot(travelId, mockMember, mockPoint, false, "anyTitle", "아무말",
                mockAttachFiles,mockAttachFile1,
                mockPlace, 5.0);

        Comment mockParentComment = new Comment(parentCommentId, "content", mockMember, mockSpot);

        when(commentRepository.findParentCommentsByTravelId(anyLong())).thenReturn(
            List.of(mockParentComment));

        assertThat(commentService.getParentCommentList(travelId)).isInstanceOf(List.class);
        assertThat(commentService.getParentCommentList(travelId).get(0)).isInstanceOf(
            CommentResponse.class);
        assert (commentService.getParentCommentList(travelId).size() == 1);
    }

    @DisplayName("getReplyCommentList 메서드가 유효한 인자를 받았을 때 comment list 가 잘 반환되는지")
    @Test
    void givenValidParameter_whenGetReplyCommentList_thenReturnCommentResponse() {
        Long travelId = 1L;
        Long parentCommentId = 1L;

        Long memberId = 1L;
        Member mockMember = new Member(memberId, "shin@gmail.com", "123456", "똷", "profile.jpg",
                SocialLoginType.KAKAO);

        GeometryFactory geometryFactory = new GeometryFactory();
        Point mockPoint = geometryFactory.createPoint(new Coordinate(0, 0));
        Place mockPlace = new Place("anyName", "anyImageUrl", mockPoint, "anyDescription");
        AttachFile mockAttachFile1 = new AttachFile(FileType.IMAGE, "fileUrl1", "originalFileName1",
                1L);
        AttachFile mockAttachFile2 = new AttachFile(FileType.IMAGE, "fileUrl2", "originalFileName2",
                2L);
        AttachFiles mockAttachFiles = new AttachFiles(List.of(mockAttachFile1, mockAttachFile2));

        Spot mockSpot = new Spot(travelId, mockMember, mockPoint, false, "anyTitle", "아무말",
                mockAttachFiles,mockAttachFile1,
                mockPlace, 5.0);

        Comment mockParentComment = new Comment(parentCommentId, "content", mockMember, mockSpot);
        Comment mockComment1 = new Comment(2L, "content", mockMember, mockSpot, mockParentComment);
        Comment mockComment2 = new Comment(3L, "content", mockMember, mockSpot, mockParentComment);
        Comment mockComment3 = new Comment(4L, "content", mockMember, mockSpot, mockParentComment);

        when(commentRepository.findChildCommentsByTravelIdAndParentId(anyLong(), anyLong())).thenReturn(
                List.of(mockComment1, mockComment2, mockComment3));

        assertThat(commentService.getChildCommentList(travelId, parentCommentId)).isInstanceOf(
                List.class);
        assertThat(
                commentService.getChildCommentList(travelId, parentCommentId).get(0)).isInstanceOf(
                CommentResponse.class);
        assert (commentService.getChildCommentList(travelId, parentCommentId).size() == 3);
    }
}