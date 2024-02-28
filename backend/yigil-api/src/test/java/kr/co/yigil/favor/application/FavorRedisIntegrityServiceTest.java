package kr.co.yigil.favor.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import kr.co.yigil.favor.domain.FavorCount;
import kr.co.yigil.favor.domain.repository.FavorCountRepository;
import kr.co.yigil.favor.domain.repository.FavorRepository;
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

class FavorRedisIntegrityServiceTest {

    @Mock
    private FavorRepository favorRepository;

    @Mock
    private FavorCountRepository favorCountRepository;

    @InjectMocks
    private FavorRedisIntegrityService favorRedisIntegrityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("ensureFavorCounts 메서드가 이미 존재하는 FavorCount를 잘 반환하는지")
    @Test
    void testEnsureLikeCountsWhenAlreadyExists() {
        Long memberId = 1L;
        Long travelId = 1L;
        Member mockMember = new Member(memberId, "shin@gmail.com", "123456", "똷", "profile.jpg",
            SocialLoginType.KAKAO);

        GeometryFactory geometryFactory = new GeometryFactory();
        Point mockPoint = geometryFactory.createPoint(new Coordinate(0, 0));

        Place mockPlace = new Place("anyName", "anyImageUrl", 0.0, mockPoint, null, null);
        AttachFile mockAttachFile1 = new AttachFile(FileType.IMAGE, "fileUrl1", "originalFileName1",
            1L);
        AttachFile mockAttachFile2 = new AttachFile(FileType.IMAGE, "fileUrl2", "originalFileName2",
            2L);
        AttachFiles mockAttachFiles = new AttachFiles(List.of(mockAttachFile1, mockAttachFile2));

        Spot spot1 = new Spot(travelId, mockMember, mockPoint, false, "anyTitle", "아무말",
            mockAttachFiles,
            mockPlace, 5.0);

        FavorCount existingFavorCount = new FavorCount(travelId, 10);

        when(favorCountRepository.findByTravelId(spot1.getId())).thenReturn(
            Optional.of(existingFavorCount));

        FavorCount result = favorRedisIntegrityService.ensureFavorCounts(spot1);

        assertThat(result).isEqualTo(existingFavorCount);
        verify(favorRepository, never()).countByTravelId(spot1.getId());
        verify(favorCountRepository, never()).save(any());
    }

    @DisplayName("ensureLikeCounts 메서드가 존재하지 않을 경우 LikeCount를 생성하고 저장하는지")
    @Test
    void testEnsureLikeCountsWhenNotExists() {
        Long memberId = 1L;
        Long travelId = 1L;
        Member mockMember = new Member(memberId, "shin@gmail.com", "123456", "똷", "profile.jpg",
            SocialLoginType.KAKAO);

        GeometryFactory geometryFactory = new GeometryFactory();
        Point mockPoint = geometryFactory.createPoint(new Coordinate(0, 0));

        Place mockPlace = new Place("anyName", "anyImageUrl", 0.0, mockPoint, null, null);
        AttachFile mockAttachFile1 = new AttachFile(FileType.IMAGE, "fileUrl1", "originalFileName1",
            1L);
        AttachFile mockAttachFile2 = new AttachFile(FileType.IMAGE, "fileUrl2", "originalFileName2",
            2L);
        AttachFiles mockAttachFiles = new AttachFiles(List.of(mockAttachFile1, mockAttachFile2));

        Spot spot1 = new Spot(travelId, mockMember, mockPoint, false, "anyTitle", "아무말",
            mockAttachFiles,
            mockPlace, 5.0);

        FavorCount newFavorCount = new FavorCount(travelId, 10);

        when(favorCountRepository.findByTravelId(travelId)).thenReturn(Optional.empty());
        when(favorRepository.countByTravelId(spot1.getId())).thenReturn(10);
        when(favorCountRepository.save(any())).thenReturn(newFavorCount);

        FavorCount count = favorRedisIntegrityService.ensureFavorCounts(spot1);

        assertThat(count.getFavorCount()).isEqualTo(newFavorCount.getFavorCount());
        verify(favorRepository).countByTravelId(spot1.getId());
        verify(favorCountRepository).save(any());
    }
}
