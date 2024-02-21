package kr.co.yigil.favor.application;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import kr.co.yigil.favor.domain.Favor;
import kr.co.yigil.favor.domain.FavorCount;
import kr.co.yigil.favor.domain.repository.FavorCountRepository;
import kr.co.yigil.favor.domain.repository.FavorRepository;
import kr.co.yigil.file.AttachFile;
import kr.co.yigil.file.AttachFiles;
import kr.co.yigil.file.FileType;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.SocialLoginType;
import kr.co.yigil.member.repository.MemberRepository;
import kr.co.yigil.notification.application.NotificationService;
import kr.co.yigil.notification.domain.Notification;
import kr.co.yigil.place.Place;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.domain.Travel;
import kr.co.yigil.travel.application.TravelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class FavorServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private FavorCountRepository favorCountRepository;

    @Mock
    private FavorRepository favorRepository;

    @Mock
    private NotificationService notificationService;

    @Mock
    private FavorRedisIntegrityService favorRedisIntegrityService;
    @Mock
    private TravelService travelService;

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
        Long travelId = 1L;
        Member mockMember = new Member(memberId, "shin@gmail.com", "123456", "똷", "profile.jpg",
            SocialLoginType.KAKAO);

        GeometryFactory geometryFactory = new GeometryFactory();
        Point mockPoint = geometryFactory.createPoint(new Coordinate(0, 0));

        Place mockPlace = new Place("anyName", "anyImageUrl", mockPoint, null, null);
        AttachFile mockAttachFile1 = new AttachFile(FileType.IMAGE, "fileUrl1", "originalFileName1",
            1L);
        AttachFile mockAttachFile2 = new AttachFile(FileType.IMAGE, "fileUrl2", "originalFileName2",
            2L);
        AttachFiles mockAttachFiles = new AttachFiles(List.of(mockAttachFile1, mockAttachFile2));

        Spot spot1 = new Spot(travelId, mockMember, mockPoint, false, "anyTitle", "아무말",
            mockAttachFiles,
            mockPlace, 5.0);

        FavorCount favorCount = new FavorCount(travelId, 1);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(mockMember));
        when(travelService.findTravelById(travelId)).thenReturn(spot1);
        when(favorRedisIntegrityService.ensureFavorCounts(spot1)).thenReturn(favorCount);

        favorService.addFavor(memberId, travelId);

        verify(favorRepository, times(1)).save(any(Favor.class));
        verify(notificationService, times(1)).sendNotification(any(Notification.class));
    }

    @DisplayName("addFavor 메서드가 존재하지 않는 게시글에 대한 요청을 보냈을 때 예외가 잘 발생하는지")
    @Test
    void whenAddFavorWithInvalidPostInfo_ShouldThrowException() {
        Long nonExisitingId = 3L;
        when(travelService.findTravelById(nonExisitingId)).thenThrow(BadRequestException.class);

        assertThrows(BadRequestException.class, () -> favorService.addFavor(1L, nonExisitingId));
    }

    @DisplayName("deleteFavor 메서드가 좋아요 정보를 잘 삭제하는지")
    @Test
    void whenDeleteFavor_ShouldDeleteFavor() {
        Long memberId = 1L;
        Long travelId = 1L;
        Member mockMember = new Member(memberId, "shin@gmail.com", "123456", "똷", "profile.jpg",
            SocialLoginType.KAKAO);

        GeometryFactory geometryFactory = new GeometryFactory();
        Point mockPoint = geometryFactory.createPoint(new Coordinate(0, 0));

        Place mockPlace = new Place("anyName", "anyImageUrl", mockPoint, null, null);
        AttachFile mockAttachFile1 = new AttachFile(FileType.IMAGE, "fileUrl1", "originalFileName1",
            1L);
        AttachFile mockAttachFile2 = new AttachFile(FileType.IMAGE, "fileUrl2", "originalFileName2",
            2L);
        AttachFiles mockAttachFiles = new AttachFiles(List.of(mockAttachFile1, mockAttachFile2));

        Spot spot1 = new Spot(travelId, mockMember, mockPoint, false, "anyTitle", "아무말",
            mockAttachFiles,
            mockPlace, 5.0);
        FavorCount favorCount = new FavorCount(travelId, 1);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(mockMember));
        when(travelService.findTravelById(travelId)).thenReturn(spot1);
        when(favorCountRepository.findById(travelId)).thenReturn(Optional.of(favorCount));
        when(favorRedisIntegrityService.ensureFavorCounts(spot1)).thenReturn(favorCount);

        favorService.deleteFavor(memberId, travelId);

        verify(favorRepository, times(1)).deleteByMemberAndTravel(any(Member.class),
            any(Travel.class));
    }
}
