package kr.co.yigil.travel.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import kr.co.yigil.comment.application.CommentRedisIntegrityService;
import kr.co.yigil.comment.application.CommentService;
import kr.co.yigil.comment.domain.CommentCount;
import kr.co.yigil.favor.application.FavorRedisIntegrityService;
import kr.co.yigil.favor.domain.FavorCount;
import kr.co.yigil.file.AttachFile;
import kr.co.yigil.file.AttachFiles;
import kr.co.yigil.file.FileType;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.application.MemberService;
import kr.co.yigil.place.Place;
import kr.co.yigil.place.application.PlaceService;
import kr.co.yigil.travel.Spot;
import kr.co.yigil.travel.dto.request.SpotCreateRequest;
import kr.co.yigil.travel.dto.response.SpotFindListResponse;
import kr.co.yigil.travel.repository.SpotRepository;
import org.junit.jupiter.api.BeforeEach;
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

public class SpotServiceTest {
    @InjectMocks
    private SpotService spotService;
    @Mock
    private SpotRepository spotRepository;
    @Mock
    private MemberService memberService;
    @Mock
    private CommentService commentService;
    @Mock
    private PlaceService placeService;
    @Mock
    private CommentRedisIntegrityService commentRedisIntegrityService;
    @Mock
    private FavorRedisIntegrityService favorRedisIntegrityService;

    @BeforeEach

    @DisplayName("getSpotList 메서드가 올바른 응답을 내리는지")
    @Test
    void GivenValidPlaceId_WhenGetSpotListTest_ThenReturnSpotFindListResponse() {
        Member mockMember = new Member("shin@gmail.com", "123456", "똷", "profile.jpg", "kakao");
        GeometryFactory geometryFactory = new GeometryFactory();
        Point mockPoint = geometryFactory.createPoint(new Coordinate(0,0));
        Place mockPlace = new Place("anyName", "anyImageUrl", mockPoint, "anyDescription");
        AttachFile mockAttachFile1 = new AttachFile(FileType.IMAGE, "fileUrl1", "originalFileName1", 1L);
        AttachFile mockAttachFile2 = new AttachFile(FileType.IMAGE, "fileUrl2", "originalFileName2", 2L);
        AttachFiles mockAttachFiles = new AttachFiles(List.of(mockAttachFile1, mockAttachFile2));

        Spot spot1 = new Spot(1L, mockMember, mockPoint, false, "anyTitle", "아무말",mockAttachFiles, mockPlace, 5.0);
        Spot spot2 = new Spot(2L, mockMember, mockPoint, false, "anyTitle", "아무말",mockAttachFiles, mockPlace, 5.0);

        List<Spot> spotList = Arrays.asList(spot1, spot2);

        Long placeId = 1L;

        when(spotRepository.findAllByPlaceIdAndIsInCourseFalse(anyLong())).thenReturn(spotList);

        when(favorRedisIntegrityService.ensureFavorCounts(spot1)).thenReturn( new FavorCount(spot1.getId(), 4));
        when(favorRedisIntegrityService.ensureFavorCounts(spot2)).thenReturn( new FavorCount(spot2.getId(), 3));

        when(commentRedisIntegrityService.ensureCommentCount(spot1)).thenReturn( new CommentCount(spot1.getId(), 2));
        when(commentRedisIntegrityService.ensureCommentCount(spot2)).thenReturn( new CommentCount(spot2.getId(), 1));

        // Act
        SpotFindListResponse spotFindListResponse = spotService.getSpotList(placeId);

        // Assert
        assertThat(spotFindListResponse).isInstanceOf(SpotFindListResponse.class);
        assertThat(spotFindListResponse.getSpotFindDtos()).hasSize(2);
        assertThat(spotFindListResponse.getSpotFindDtos()).extracting("spotId").containsExactlyInAnyOrder(spot1.getId(), spot2.getId());
    }

    @DisplayName("createSpot 메서드가 유효한 인자를 넘겨받았을 때 올바른 응답을 내리는지")
    @Test
    void GivenValidInput_WhenCreateSpotTest_ThenReturnSpotCreateResponse() {
        // Arrange
        Long memberId = 1L;
        SpotCreateRequest spotCreateRequest = createMockSpotCreateRequest();

        // Mocked Member data
        Member mockMember = createMockMember();

        // Mocked Place data
        Place mockPlace = createMockPlace();

        // Mocked FileUploadEvent
        FileUploadEvent mockFileUploadEvent = createMockFileUploadEvent(mockPlace, spotCreateRequest);

        // Mocked favorRedisIntegrityService.ensureFavorCounts
        when(favorRedisIntegrityService.ensureFavorCounts(any())).thenReturn(new FavorCounts(1, 2));

        // Mocked memberService.findMemberById
        when(memberService.findMemberById(memberId)).thenReturn(mockMember);

        // Mocked placeRepository.findByName
        when(placeRepository.findByName(spotCreateRequest.getPlaceName())).thenReturn(Optional.of(mockPlace));

        // Mocked spotRepository.save
        when(spotRepository.save(any(Spot.class))).thenReturn(createMockSpot());

        // Mocked applicationEventPublisher.publishEvent
        doAnswer(invocation -> {
            FileUploadEvent event = invocation.getArgument(0);
            event.getCallback().accept("mockUrl");
            return null;
        }).when(applicationEventPublisher).publishEvent(any(FileUploadEvent.class));

        // Act
        SpotCreateResponse result = spotService.createSpot(memberId, spotCreateRequest);

        // Assert
        assertThat(result.getMessage()).isEqualTo("스팟 정보 생성 성공");
        // Add more assertions based on your logic and expected behavior
    }

    private SpotCreateRequest createMockSpotCreateRequest() {
    }

    // Helper methods for creating mock data

}
