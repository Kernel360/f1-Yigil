package kr.co.yigil.comment.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import kr.co.yigil.comment.domain.CommentCount;
import kr.co.yigil.comment.domain.repository.CommentCountRepository;
import kr.co.yigil.comment.domain.repository.CommentRepository;
import kr.co.yigil.file.AttachFile;
import kr.co.yigil.file.AttachFiles;
import kr.co.yigil.file.FileType;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.SocialLoginType;
import kr.co.yigil.place.domain.Place;
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

class CommentRedisIntegrityServiceTest {

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
        Long travelId = 1L;
        Member mockMember = new Member(memberId, "shin@gmail.com", "123456", "똷", "profile.jpg",
            SocialLoginType.KAKAO);

        GeometryFactory geometryFactory = new GeometryFactory();
        Point mockPoint = geometryFactory.createPoint(new Coordinate(0, 0));

        AttachFile mockStaticImgFile = new AttachFile(FileType.IMAGE, "fileUrl", "originalFileName",
            1L);
        Place mockPlace = new Place("anyName", "anyImageUrl", mockPoint, null,
            mockStaticImgFile);
        AttachFile mockAttachFile1 = new AttachFile(FileType.IMAGE, "fileUrl1", "originalFileName1",
            1L);
        AttachFile mockAttachFile2 = new AttachFile(FileType.IMAGE, "fileUrl2", "originalFileName2",
            2L);
        AttachFiles mockAttachFiles = new AttachFiles(List.of(mockAttachFile1, mockAttachFile2));

        Spot spot1 = new Spot(travelId, mockMember, mockPoint, false, "anyTitle", "아무말",
            mockAttachFiles, mockPlace, 5.0);
        CommentCount existingCommentCount = new CommentCount(travelId, 5);

        when(commentCountRepository.findByTravelId(travelId)).thenReturn(
            Optional.of(existingCommentCount));

        CommentCount result = commentRedisIntegrityService.ensureCommentCount(spot1);

        assertThat(result).isEqualTo(existingCommentCount);
        verify(commentRepository, never()).countNonDeletedCommentsByTravelId(spot1.getId());
        verify(commentCountRepository, never()).save(any());
    }

    @DisplayName("ensureCommentCount 메서드가 존재하지 않을 경우 CommentCount를 생성하고 저장하는지")
    @Test
    void testEnsureCommentCountWhenNotExists() {
        Long memberId = 1L;
        Long travelId = 1L;
        Member mockMember = new Member(memberId, "shin@gmail.com", "123456", "똷", "profile.jpg",
            SocialLoginType.KAKAO);

        GeometryFactory geometryFactory = new GeometryFactory();
        Point mockPoint = geometryFactory.createPoint(new Coordinate(0, 0));
        AttachFile mockStaticImgFile = new AttachFile(FileType.IMAGE, "fileUrl", "originalFileName",
            1L);
        Place mockPlace = new Place("anyName", "anyImageUrl", mockPoint, null,
            mockStaticImgFile);
        AttachFile mockAttachFile1 = new AttachFile(FileType.IMAGE, "fileUrl1", "originalFileName1",
            1L);
        AttachFile mockAttachFile2 = new AttachFile(FileType.IMAGE, "fileUrl2", "originalFileName2",
            2L);
        AttachFiles mockAttachFiles = new AttachFiles(List.of(mockAttachFile1, mockAttachFile2));

        Spot spot1 = new Spot(travelId, mockMember, mockPoint, false, "anyTitle", "아무말",
            mockAttachFiles, mockPlace, 5.0);

        CommentCount newCommentCount = new CommentCount(travelId, 10);

        when(commentCountRepository.findByTravelId(spot1.getId())).thenReturn(Optional.empty());
        when(commentRepository.countNonDeletedCommentsByTravelId(spot1.getId())).thenReturn(10);
        when(commentCountRepository.save(any())).thenReturn(newCommentCount);

        CommentCount count = commentRedisIntegrityService.ensureCommentCount(spot1);

        assertThat(count.getCommentCount()).isEqualTo(newCommentCount.getCommentCount());
        verify(commentRepository).countNonDeletedCommentsByTravelId(spot1.getId());
        verify(commentCountRepository).save(any());
    }
}
