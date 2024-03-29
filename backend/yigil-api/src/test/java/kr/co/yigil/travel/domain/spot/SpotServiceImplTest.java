package kr.co.yigil.travel.domain.spot;

import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.file.AttachFile;
import kr.co.yigil.file.AttachFiles;
import kr.co.yigil.file.FileType;
import kr.co.yigil.file.FileUploader;
import kr.co.yigil.follow.domain.FollowReader;
import kr.co.yigil.global.Selected;
import kr.co.yigil.global.exception.AuthException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.domain.MemberReader;
import kr.co.yigil.place.domain.Place;
import kr.co.yigil.place.domain.PlaceCacheStore;
import kr.co.yigil.place.domain.PlaceReader;
import kr.co.yigil.place.domain.PlaceStore;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.domain.course.CourseInfo;
import kr.co.yigil.travel.domain.dto.SpotListDto;
import kr.co.yigil.travel.domain.spot.SpotCommand.ModifySpotRequest;
import kr.co.yigil.travel.domain.spot.SpotCommand.RegisterPlaceRequest;
import kr.co.yigil.travel.domain.spot.SpotCommand.RegisterSpotRequest;
import kr.co.yigil.travel.domain.spot.SpotInfo.Main;
import kr.co.yigil.travel.domain.spot.SpotInfo.MySpot;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Point;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SpotServiceImplTest {

    @Mock
    private MemberReader memberReader;
    @Mock
    private SpotReader spotReader;
    @Mock
    private PlaceReader placeReader;

    @Mock
    private SpotStore spotStore;
    @Mock
    private PlaceStore placeStore;
    @Mock
    private PlaceCacheStore placeCacheStore;

    @Mock
    private SpotSeriesFactory spotSeriesFactory;
    @Mock
    private FileUploader fileUploader;
    @Mock
    private FollowReader followReader;

    @InjectMocks
    private SpotServiceImpl spotService;

    @DisplayName("getSpotSliceInPlace 메서드가 Spot의 Slice를 잘 반환하는지")
    @Test
    void getSpotSliceInPlace_ShouldReturnSlice() {
        Long placeId = 1L;
        Pageable pageable = mock(Pageable.class);
        Accessor mockAccessor = mock(Accessor.class);
        Slice<Spot> expectedSlice = mock(Slice.class);

        when(spotReader.getSpotSliceInPlace(placeId, pageable)).thenReturn(expectedSlice);

        SpotInfo.Slice result = spotService.getSpotSliceInPlace(placeId, mockAccessor, pageable);

        verify(spotReader).getSpotSliceInPlace(placeId, pageable);
    }

    @DisplayName("retrieveMySpotInfoInPlace 메서드가 존재하는 Spot에 대한 응답을 잘 반환하는지")
    @Test
    void retrieveMySpotInfoInPlace_ShouldReturnResponse_WithExistingSpot() {
        Spot mockSpot = mock(Spot.class);
        Optional<Spot> optionalSpot = Optional.of(mockSpot);
        AttachFiles attachFiles = mock(AttachFiles.class);

        when(mockSpot.getAttachFiles()).thenReturn(attachFiles);
        when(attachFiles.getUrls()).thenReturn(List.of("images/image.jpg", "images/thumb.jpg"));
        when(mockSpot.getRate()).thenReturn(3.5);
        when(mockSpot.getCreatedAt()).thenReturn(LocalDateTime.now());
        when(mockSpot.getDescription()).thenReturn("description");

        when(spotReader.findSpotByPlaceIdAndMemberId(anyLong(), anyLong())).thenReturn(optionalSpot);

        MySpot result = spotService.retrieveMySpotInfoInPlace(1L, 1L);
        assertNotNull(result);
        assertTrue(result.isExists());
    }

    @DisplayName("retrieveMySpotInfoInPlace 메서드가 존재하지 않는 Spot에 대한 응답을 잘 반환하는지")
    @Test
    void retrieveMySpotInfoInPlace_ShouldReturnResponse_WithNonExistingSpot() {
        Optional<Spot> optionalSpot = mock(Optional.class);
        when(optionalSpot.isEmpty()).thenReturn(true);

        when(spotReader.findSpotByPlaceIdAndMemberId(anyLong(), anyLong())).thenReturn(optionalSpot);

        MySpot result = spotService.retrieveMySpotInfoInPlace(1L, 1L);

        assertNotNull(result);
        assertFalse(result.isExists());
    }

    @DisplayName("registerSpot 메서드가 이미 존재하는 Place에 대한 요청을 통해 Spot을 잘 저장하는지")
    @Test
    void registerSpot_WithExistingPlace_ShouldStoreSpot() {
        RegisterSpotRequest command = mock(RegisterSpotRequest.class);
        RegisterPlaceRequest placeCommand = mock(RegisterPlaceRequest.class);
        Long memberId = 1L;
        Member member = mock(Member.class);
        Long placeId = 1L;
        String placeName = "Test Place";
        String placeAddress = "Test Address";
        Place place = new Place(placeId, placeName, placeAddress, null, null, null, null);
        Spot spot = mock(Spot.class);
        AttachFiles mockAttachFiles = mock(AttachFiles.class);

        when(command.getRegisterPlaceRequest()).thenReturn(placeCommand);
        when(spotSeriesFactory.initAttachFiles(command)).thenReturn(mockAttachFiles);
        when(command.toEntity(member, place, false, mockAttachFiles)).thenReturn(spot);
        when(placeCommand.getPlaceName()).thenReturn(placeName);
        when(placeCommand.getPlaceAddress()).thenReturn(placeAddress);
        when(memberReader.getMember(memberId)).thenReturn(member);
        when(placeReader.findPlaceByNameAndAddress(anyString(), anyString())).thenReturn(Optional.of(place));
        when(spotStore.store(any(Spot.class))).thenReturn(spot);

        spotService.registerSpot(command, memberId);

        verify(spotStore).store(any(Spot.class));
        verify(placeCacheStore).incrementSpotCountInPlace(anyLong());
    }

    @DisplayName("registerSpot 메서드가 새로운 Place와 Spot을 잘 저장하는지")
    @Test
    void registerSpot_WithNewPlace_ShouldRegisterPlaceAndSpot() {
        RegisterSpotRequest command = mock(RegisterSpotRequest.class);
        Long memberId = 1L;
        Member member = mock(Member.class);
        Long placeId = 1L;
        String placeName = "Test Place";
        String placeAddress = "Test Address";
        Place place = new Place(placeId, placeName, placeAddress, null, null, null, null);
        Spot spot = mock(Spot.class);
        RegisterPlaceRequest placeCommand = mock(RegisterPlaceRequest.class);
        AttachFiles mockAttachFiles = new AttachFiles(List.of(mock(AttachFile.class)));

        when(memberReader.getMember(memberId)).thenReturn(member);
        when(command.getRegisterPlaceRequest()).thenReturn(placeCommand);
        when(spotSeriesFactory.initAttachFiles(command)).thenReturn(mockAttachFiles);
        when(command.toEntity(member, place, false, mockAttachFiles)).thenReturn(spot);
        when(placeCommand.getPlaceName()).thenReturn(placeName);
        when(placeCommand.getPlaceAddress()).thenReturn(placeAddress);
        when(placeReader.findPlaceByNameAndAddress(anyString(), anyString())).thenReturn(Optional.empty());
        when(placeStore.store(any())).thenReturn(place);
        when(spotStore.store(any(Spot.class))).thenReturn(spot);

        spotService.registerSpot(command, memberId);

        verify(fileUploader, times(1)).upload(any());
        verify(placeStore).store(any());
        verify(spotStore).store(any(Spot.class));
    }

    @DisplayName("retrieveSpotInfo 메서드가 SpotInfo를 잘 반환하는지")
    @Test
    void retrieveSpotInfo_ShouldReturnSpotInfo() {
        Long spotId = 1L;
        Spot spot = mock(Spot.class);
        Place place = mock(Place.class);
        AttachFiles attachFiles = mock(AttachFiles.class);
        Member member = mock(Member.class);

        when(spotReader.getSpot(spotId)).thenReturn(spot);
        when(spot.getPlace()).thenReturn(place);
        when(spot.getAttachFiles()).thenReturn(attachFiles);
        when(place.getMapStaticImageFileUrl()).thenReturn("~~");
        when(spot.getMember()).thenReturn(member);
        when(member.getProfileImageUrl()).thenReturn("image.utl");
        when(member.getNickname()).thenReturn("nickname");

        Main result = spotService.retrieveSpotInfo(spotId);

        assertNotNull(result);
    }

    @DisplayName("modifySpot 메서드가 유효한 memberId가 주어졌을 때 spot을 잘 수정하는지")
    @Test
    void modifySpot_WithValidMemberId_ShouldModifySpot() {
        ModifySpotRequest command = mock(ModifySpotRequest.class);
        Long spotId = 1L;
        Long memberId = 1L;
        Spot spot = mock(Spot.class);
        Member member = mock(Member.class);

        when(spot.getMember()).thenReturn(member);
        when(spotReader.getSpot(spotId)).thenReturn(spot);
        when(member.getId()).thenReturn(memberId);

        spotService.modifySpot(command, spotId, memberId);

        verify(spotSeriesFactory).modify(command, spot);
    }

    @DisplayName("modifySpot 메서드가 유효하지 않은 memberId가 주어졌을 때 예외를 잘 발생시키는지")
    @Test
    void modifySpot_WithInvalidMemberId_ShouldThrowAuthException() {
        ModifySpotRequest command = mock(ModifySpotRequest.class);
        Long spotId = 1L;
        Long memberId = 2L;
        Spot spot = mock(Spot.class);
        Member member = mock(Member.class);

        when(spot.getMember()).thenReturn(member);
        when(member.getId()).thenReturn(1L);
        when(spotReader.getSpot(spotId)).thenReturn(spot);

        Exception exception = assertThrows(AuthException.class, () -> {
            spotService.modifySpot(command, spotId, memberId);
        });

        assertEquals(ExceptionCode.INVALID_AUTHORITY.getMessage(), exception.getMessage());
    }

    @DisplayName("deleteSpot 메서드가 유효한 memberId가 주어졌을 때 spot을 잘 삭제하는지")
    @Test
    void deleteSpot_WithValidMemberId_ShouldRemoveSpot() {
        Long spotId = 1L;
        Long memberId = 1L;
        Spot spot = mock(Spot.class);
        Member member = mock(Member.class);

        when(spot.getMember()).thenReturn(member);
        when(member.getId()).thenReturn(memberId);
        when(spotReader.getSpot(spotId)).thenReturn(spot);

        spotService.deleteSpot(spotId, memberId);

        verify(spotStore).remove(spot);
    }

    @DisplayName("deleteSpot 메서드가 유효하지 않은 memberId가 주어졌을 때 예외를 잘 발생시키는지")
    @Test
    void deleteSpot_WithInvalidMemberId_ShouldThrowAuthException() {
        Long spotId = 1L;
        Long memberId = 2L;
        Spot spot = mock(Spot.class);
        Member member = mock(Member.class);

        when(spot.getMember()).thenReturn(member);
        when(member.getId()).thenReturn(1L);
        when(spotReader.getSpot(spotId)).thenReturn(spot);

        Exception exception = assertThrows(AuthException.class, () -> {
            spotService.deleteSpot(spotId, memberId);
        });

        assertEquals(ExceptionCode.INVALID_AUTHORITY.getMessage(), exception.getMessage());
    }

    @DisplayName("retrieveSpotList 를 호출했을 때 스팟 리스트 조회가 잘 되는지 확인")
    @Test
    void WhenRetrieveSpotList_ThenShouldReturnSpotListResponse() {
        Long memberId = 1L;
        Long spotId = 1L;
        PageRequest pageable = PageRequest.of(0, 10);
        AttachFile mockAttachFile = new AttachFile(mock(FileType.class), "fileUrl",
                "originalFileName", 100L);

        SpotListDto mockSpotListDto = new SpotListDto(spotId, 1L, "title", 3.5, "fileUrl",
                LocalDateTime.now(), false);
        PageImpl<SpotListDto> mockSpotList = new PageImpl<>(List.of(mockSpotListDto));

        when(spotReader.getMemberSpotList(anyLong(), any(Selected.class), any())).thenReturn(mockSpotList);

        var result = spotService.retrieveSpotList(memberId, Selected.PRIVATE, pageable);

        assertThat(result).isNotNull().isInstanceOf(SpotInfo.MySpotsResponse.class);
        assertThat(result.getContent().getFirst()).isInstanceOf(SpotInfo.SpotListInfo.class);
    }

    @DisplayName("getMySpotsDetailInfo 를 호출했을 때 마이 스팟 상세 정보 조회가 잘 되는지 확인")
    @Test
    void WhenGetMySpotsDetailInfo_ThenShouldReturnMySpotsInfo() {
        Long memberId = 1L;
        List<Long> spotIds = List.of(1L, 2L);

        Spot spot1 = mock(Spot.class);

        Place place1 = mock(Place.class);
        when(spot1.getId()).thenReturn(1L);
        when(spot1.getPlace()).thenReturn(place1);
        when(place1.getName()).thenReturn("placeName");
        when(place1.getAddress()).thenReturn("placeAddress");
        when(spot1.getDescription()).thenReturn("description");
        AttachFiles mockAttachFiles = mock(AttachFiles.class);
        when(spot1.getAttachFiles()).thenReturn(mockAttachFiles);
        when(mockAttachFiles.getUrls()).thenReturn(List.of("imageUrl"));
        when(spot1.getRate()).thenReturn(3.5);
        when(spot1.getCreatedAt()).thenReturn(LocalDateTime.now());
        Point point1 = mock(Point.class);
        when(spot1.getLocation()).thenReturn(point1);
        when(point1.getX()).thenReturn(1.0);
        when(point1.getY()).thenReturn(1.0);

        Spot spot2 = mock(Spot.class);
        when(spot2.getId()).thenReturn(2L);
        when(spot2.getPlace()).thenReturn(place1);
        when(place1.getName()).thenReturn("placeName");
        when(place1.getAddress()).thenReturn("placeAddress");
        when(spot2.getDescription()).thenReturn("description");
        when(spot2.getAttachFiles()).thenReturn(mockAttachFiles);
        when(mockAttachFiles.getUrls()).thenReturn(List.of("imageUrl"));
        when(spot2.getRate()).thenReturn(3.5);
        when(spot2.getCreatedAt()).thenReturn(LocalDateTime.now());
        when(spot2.getLocation()).thenReturn(point1);
        Point point2 = mock(Point.class);
        when(spot2.getLocation()).thenReturn(point2);
        when(point2.getX()).thenReturn(1.0);
        when(point2.getY()).thenReturn(1.0);

        List<Spot> spots = List.of(spot1, spot2);

        when(spotReader.isExistSpot(anyLong(), anyLong())).thenReturn(true);
        when(spotReader.getSpots(anyList())).thenReturn(spots);


        when(spotReader.isExistSpot(anyLong(), anyLong())).thenReturn(true);
        when(spotReader.getSpots(anyList())).thenReturn(spots);

        var result = spotService.getMySpotsDetailInfo(spotIds, memberId);

        assertThat(result).isNotNull().isInstanceOf(CourseInfo.MySpotsInfo.class);
        assertThat(result.getMySpotDetailDtoList().size()).isEqualTo(2);
    }


    @DisplayName("getFavoriteSpotsInfo 를 호출했을 때 즐겨찾기 스팟 정보 조회가 잘 되는지 확인")
    @Test
    void getFavoriteSpotsInfo() {
        Long memberId = 1L;
        PageRequest pageable = PageRequest.of(0, 10);
        Spot spot = mock(Spot.class);
        Place place = mock(Place.class);
        Member member = mock(Member.class);

        when(spotReader.getFavoriteSpotList(anyLong(), any())).thenReturn(new PageImpl<>(List.of(spot)));
        when(spot.getPlace()).thenReturn(place);
        when(spot.getMember()).thenReturn(member);
        when(member.getProfileImageUrl()).thenReturn("image.utl");
        when(member.getNickname()).thenReturn("nickname");
        when(spot.getRate()).thenReturn(3.5);
        when(spot.getCreatedAt()).thenReturn(LocalDateTime.now());
        when(followReader.isFollowing(anyLong(), anyLong())).thenReturn(true);

        var result = spotService.getFavoriteSpotsInfo(memberId, pageable);

        assertThat(result).isNotNull().isInstanceOf(SpotInfo.MyFavoriteSpotsInfo.class);
        assertThat(result.getContents().getFirst()).isInstanceOf(SpotInfo.FavoriteSpotInfo.class);
    }
}


