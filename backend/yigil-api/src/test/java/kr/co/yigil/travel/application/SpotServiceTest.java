package kr.co.yigil.travel.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import kr.co.yigil.comment.application.CommentRedisIntegrityService;
import kr.co.yigil.comment.application.CommentService;
import kr.co.yigil.comment.domain.CommentCount;
import kr.co.yigil.favor.application.FavorRedisIntegrityService;
import kr.co.yigil.favor.domain.FavorCount;
import kr.co.yigil.file.AttachFile;
import kr.co.yigil.file.AttachFiles;
import kr.co.yigil.file.FileType;
import kr.co.yigil.file.FileUploadEvent;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.application.MemberService;
import kr.co.yigil.place.Place;
import kr.co.yigil.place.application.PlaceService;
import kr.co.yigil.travel.Spot;
import kr.co.yigil.travel.dto.request.SpotCreateRequest;
import kr.co.yigil.travel.dto.request.SpotUpdateRequest;
import kr.co.yigil.travel.dto.response.SpotCreateResponse;
import kr.co.yigil.travel.dto.response.SpotInfoResponse;
import kr.co.yigil.travel.dto.response.SpotListResponse;
import kr.co.yigil.travel.dto.response.SpotUpdateResponse;
import kr.co.yigil.travel.repository.SpotRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

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

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;


    @DisplayName("getSpotList 메서드가 올바른 응답을 내리는지")
    @Test
    void GivenValidPlaceId_WhenGetSpotListTest_ThenReturnSpotFindListResponse() {
        Member mockMember = new Member("hoyoon@gmail.com", "123456", "회고부장", "profile.jpg",
                "kakao");
        GeometryFactory geometryFactory = new GeometryFactory();
        Point mockPoint = geometryFactory.createPoint(new Coordinate(0, 0));
        Place mockPlace = new Place("anyName", "anyImageUrl", mockPoint, "anyDescription");
        AttachFile mockAttachFile1 = new AttachFile(FileType.IMAGE, "fileUrl1", "originalFileName1",
                1L);
        AttachFile mockAttachFile2 = new AttachFile(FileType.IMAGE, "fileUrl2", "originalFileName2",
                2L);
        AttachFiles mockAttachFiles = new AttachFiles(List.of(mockAttachFile1, mockAttachFile2));

        Spot spot1 = new Spot(1L, mockMember, mockPoint, false, "anyTitle", "아무말", mockAttachFiles, mockAttachFile1,
                mockPlace, 5.0);
        Spot spot2 = new Spot(2L, mockMember, mockPoint, false, "anyTitle", "아무말", mockAttachFiles, mockAttachFile1,
                mockPlace, 5.0);

        List<Spot> spotList = Arrays.asList(spot1, spot2);

        Long placeId = 1L;

        when(spotRepository.findAllByPlaceIdAndIsInCourseFalse(anyLong())).thenReturn(spotList);

        when(favorRedisIntegrityService.ensureFavorCounts(spot1)).thenReturn(
                new FavorCount(spot1.getId(), 4));
        when(favorRedisIntegrityService.ensureFavorCounts(spot2)).thenReturn(
                new FavorCount(spot2.getId(), 3));

        when(commentRedisIntegrityService.ensureCommentCount(spot1)).thenReturn(
                new CommentCount(spot1.getId(), 2));
        when(commentRedisIntegrityService.ensureCommentCount(spot2)).thenReturn(
                new CommentCount(spot2.getId(), 1));

        // Act
        SpotListResponse spotListResponse = spotService.getSpotList(placeId);

        // Assert
        assertThat(spotListResponse).isInstanceOf(SpotListResponse.class);
        assertThat(spotListResponse.getSpotFindDtos()).hasSize(2);
        assertThat(spotListResponse.getSpotFindDtos()).extracting("spotId")
                .containsExactlyInAnyOrder(spot1.getId(), spot2.getId());
    }

    @DisplayName("createSpot 메서드가 유효한 인자를 넘겨받았을 때 올바른 응답을 내리는지")
    @Test
    void GivenValidInput_WhenCreateSpotTest_ThenReturnSpotCreateResponse() {
        // Arrange
        Long memberId = 1L;

        String mockPointJson = "{\"type\":\"Point\",\"coordinates\":[127.123456,37.123456]}";
        String mockTitle = "mockTitle";
        String mockDescription = "mockDescription";
        MultipartFile mockFile = new MockMultipartFile("mockFile", "mockFile.jpg", "image/jpeg",
                "mockFile".getBytes());
        List<MultipartFile> mockFiles = List.of(mockFile);
        String mockPlaceName = "mockPlaceName";
        String mockPlaceAddress = "mockPlaceAddress";
        String mockPlacePointJson = "{\"type\":\"Point\",\"coordinates\":[127.123456,37.123456]}";
        double mockRate = 5.0;

        SpotCreateRequest spotCreateRequest = new SpotCreateRequest(
                mockPointJson,
                mockTitle,
                mockDescription,
                mockFiles,
                mockFile,
                mockPlaceName,
                mockPlaceAddress,
                mockPlacePointJson,
                mockRate
        );

        Point mockPoint = new GeometryFactory().createPoint(new Coordinate(127.123456, 37.123456));
        Member mockMember = new Member("shin@gmail.com", "123456", "똷", "profile.jpg", "kakao");
        Place mockPlace = new Place("mockPlaceName", "mockImageUrl", mockPoint, "mockDescription");


        when(memberService.findMemberById(memberId)).thenReturn(mockMember);



        when(placeService.getOrCreatePlace(
                anyString(),
                anyString(),
                anyString()
        )).thenReturn(mockPlace);

        AttachFile mockAttachFile = new AttachFile(FileType.IMAGE, "mockUrl", "mockFileName", 1L);
        AttachFiles mockAttachFiles = new AttachFiles(List.of(mockAttachFile));

        doAnswer(invocation -> {
            FileUploadEvent event = invocation.getArgument(0);
            event.getCallback().accept(mockAttachFile);
            return null;
        }).when(applicationEventPublisher).publishEvent(any(FileUploadEvent.class));

        Spot mockSpot = new Spot(mockMember, mockPoint, false, mockTitle, mockDescription,
                mockAttachFiles, mockAttachFile,  mockPlace, mockRate);
        when(spotRepository.save(any(Spot.class))).thenReturn(mockSpot);


        // Act
        SpotCreateResponse spotCreateResponse = spotService.createSpot(memberId, spotCreateRequest);
        // Assert
        assertThat(spotCreateResponse.getMessage()).isEqualTo("스팟 정보 생성 성공");
    }

    @DisplayName("updateSpot 메서드가 유효한 인자를 넘겨받았을 때 올바른 응답을 내리는지")
    @Test
    void GivenValidInput_WhenUpdateSpotTest_ThenReturnSpotUpdateResponse() {
        Long memberId = 1L;
        Long spotId = 1L;
        String mockPointJson = "{\"type\":\"Point\",\"coordinates\":[127.123456,37.123456]}";
        String mockTitle = "mockTitle";
        String mockDescription = "mockDescription";
        MultipartFile mockFile = new MockMultipartFile("mockFile", "mockFile.jpg", "image/jpeg",
                "mockFile".getBytes());
        MultipartFile mockFil2 = new MockMultipartFile("mockFile", "mockFile.jpg", "image/jpeg",
                "mockFile".getBytes());
        List<MultipartFile> mockFiles = List.of(mockFile, mockFil2);
        String mockPlaceName = "mockPlaceName";
        String mockPlaceAddress = "mockPlaceAddress";
        String mockPlacePointJson = "{\"type\":\"Point\",\"coordinates\":[127.123456,37.123456]}";
        double mockRate = 5.0;

        SpotUpdateRequest spotUpdateRequest = new SpotUpdateRequest(
                mockPointJson,
                mockTitle,
                mockDescription,
                mockFiles,
                mockFile,
                mockPlaceName,
                mockPlaceAddress,
                mockPlacePointJson,
                mockRate
        );

        Member mockMember = new Member("shin@gmail.com", "123456", "똷", "profile.jpg", "kakao");
        when(memberService.findMemberById(memberId)).thenReturn(mockMember);

        Place mockPlace = new Place("mockPlaceName", "mockImageUrl",
                new GeometryFactory().createPoint(new Coordinate(127.123456, 37.123456)),
                "mockDescription");
        when(placeService.getOrCreatePlace(anyString(), anyString(), anyString())).thenReturn(
                mockPlace);
        AttachFile mockAttachFile = new AttachFile(FileType.IMAGE, "mockUrl", "mockFileName", 1L);

        doAnswer(invocation -> {
            FileUploadEvent event = invocation.getArgument(0);
            event.getCallback().accept(mockAttachFile);
            return null;
        }).when(applicationEventPublisher).publishEvent(any(FileUploadEvent.class));

        Spot mockSpot = new Spot(spotId, mockMember,
                new GeometryFactory().createPoint(new Coordinate(127.123456, 37.123456)), false,
                mockTitle, mockDescription, new AttachFiles(new ArrayList<>()), mockAttachFile, mockPlace,
                mockRate);
        when(spotRepository.save(any(Spot.class))).thenReturn(mockSpot);

        // Act
        SpotUpdateResponse spotUpdateResponse = spotService.updateSpot(memberId, spotId,
                spotUpdateRequest);

        // Assert
        assertThat(spotUpdateResponse).isNotNull();
        assertThat(spotUpdateResponse.getMessage()).isEqualTo("스팟 정보 수정 성공");
    }

    @DisplayName("getSpotInfo 메서드가 유효한 인자를 넘겨받았을 때 올바른 응답을 내리는지")
    @Test
    void GivenValidInput_WhenGetSpotInfoTest_ThenReturnSpotFindResponse() {
        // Arrange
        Long spotId = 1L;
        AttachFile mockAttachFile = new AttachFile(FileType.IMAGE, "mockUrl", "mockFileName", 1L);

        Spot mockSpot = new Spot(
                new Member("shin@gmail.com", "123456", "똷", "profile.jpg", "kakao"),
                new GeometryFactory().createPoint(new Coordinate(127.123456, 37.123456)), false,
                "mockTitle", "mockDescription", new AttachFiles(new ArrayList<>()), mockAttachFile,
                new Place("mockPlaceName", "mockImageUrl",
                        new GeometryFactory().createPoint(new Coordinate(127.123456, 37.123456)),
                        "mockDescription"), 5.0);
        when(spotRepository.findById(spotId)).thenReturn(Optional.of(mockSpot));

        // Act
        SpotInfoResponse spotInfoResponse = spotService.getSpotInfo(spotId);

        // Assert
        assertThat(spotInfoResponse).isNotNull();
    }
//    @DisplayName("getSpot 메서드가 없는 spotId를 넘겨받았을 대 예외를 발생시키는지")
//

}
