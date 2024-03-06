package kr.co.yigil.travel.application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import kr.co.yigil.file.AttachFile;
import kr.co.yigil.file.AttachFiles;
import kr.co.yigil.file.FileType;
import kr.co.yigil.file.FileUploader;
import kr.co.yigil.global.Selected;
import kr.co.yigil.member.Ages;
import kr.co.yigil.member.Gender;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.SocialLoginType;
import kr.co.yigil.place.domain.Place;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.domain.spot.SpotCommand.ModifySpotRequest;
import kr.co.yigil.travel.domain.spot.SpotCommand.RegisterSpotRequest;
import kr.co.yigil.travel.domain.spot.SpotInfo;
import kr.co.yigil.travel.domain.spot.SpotInfo.Main;
import kr.co.yigil.travel.domain.spot.SpotInfo.MySpot;
import kr.co.yigil.travel.domain.spot.SpotService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

@ExtendWith(MockitoExtension.class)
public class SpotFacadeTest {

    @Mock
    private SpotService spotService;

    @Mock
    private FileUploader fileUploader;

    @InjectMocks
    private SpotFacade spotFacade;

    @DisplayName("getSpotSliceInPlace 메서드가 Spot Slice를 잘 반환하는지")
    @Test
    void getSpotSliceInPlace_ShouldReturnSlice() {
        Long id = 1L;
        String email = "test@test.com";
        String socialLoginId = "12345";
        String nickname = "tester";
        String profileImageUrl = "test.jpg";
        Member member = new Member(id, email, socialLoginId, nickname, profileImageUrl,
                SocialLoginType.KAKAO, Ages.NONE, Gender.NONE);

        String title = "Test Spot Title";
        String description = "Test Course Description";
        double rate = 5.0;
        Spot spot = new Spot(id, member, null, false, title, description, null, null, rate);

        Long placeId = 1L;
        Pageable pageable = PageRequest.of(0, 10);
        Slice<Spot> expectedSlice = new PageImpl<>(Collections.singletonList(spot), pageable, 1);
        when(spotService.getSpotSliceInPlace(eq(placeId), any(Pageable.class))).thenReturn(expectedSlice);

        Slice<Spot> result = spotFacade.getSpotSliceInPlace(placeId, pageable);

        assertEquals(expectedSlice, result);
        verify(spotService).getSpotSliceInPlace(placeId, pageable);
    }

    @DisplayName("getMySpotInPlace 메서드가 응답을 잘 반환하는지")
    @Test
    void getMySpotInPlace_ShouldReturnResponse() {
        SpotInfo.MySpot mySpot = mock(MySpot.class);

        when(spotService.retrieveMySpotInfoInPlace(anyLong(), anyLong())).thenReturn(mySpot);

        spotFacade.retrieveMySpotInfoInPlace(1L, 1L);

        verify(spotService).retrieveMySpotInfoInPlace(1L, 1L);
    }

    @DisplayName("registerSpot 메서드가 SpotService를 잘 호출하는지")
    @Test
    void registerSpot_ShouldCallService() {
        RegisterSpotRequest command = mock(RegisterSpotRequest.class);
        Long memberId = 1L;

        doNothing().when(spotService).registerSpot(command, memberId);

        spotFacade.registerSpot(command, memberId);

        verify(spotService).registerSpot(command, memberId);
    }

    @DisplayName("retrieveSpotinfo 메서드가 SpotInfo를 잘 반환하는지")
    @Test
    void retrieveSpotInfo_ShouldReturnSpotInfo() {
        Long spotId = 1L;
        Main expectedSpotInfo = mock(Main.class);

        when(spotService.retrieveSpotInfo(spotId)).thenReturn(expectedSpotInfo);

        Main result = spotFacade.retrieveSpotInfo(spotId);

        assertEquals(expectedSpotInfo, result);
        verify(spotService).retrieveSpotInfo(spotId);
    }

    @DisplayName("modifySpot 메서드가 SpotService를 잘 호출하는지")
    @Test
    void modifySpot_ShouldCallService() {
        ModifySpotRequest command = mock(ModifySpotRequest.class);
        Long spotId = 1L;
        Long memberId = 1L;

        doNothing().when(spotService).modifySpot(command, spotId, memberId);

        spotFacade.modifySpot(command, spotId, memberId);

        verify(spotService).modifySpot(command, spotId, memberId);
    }

    @DisplayName("deleteSpot 메서드가 SpotService를 잘 호출하는지")
    @Test
    void deleteSpot_ShouldCallService() {
        Long spotId = 1L;
        Long memberId = 1L;

        doNothing().when(spotService).deleteSpot(spotId, memberId);

        spotFacade.deleteSpot(spotId, memberId);

        verify(spotService).deleteSpot(spotId, memberId);
    }

    @DisplayName("getMemberSpotInfo 메서드가 유효한 요청이 들어왔을 때 MemberInfo의 MemberSpotResponse 객체를 잘 반환하는지")
    @Test
    void WhenGetMemberSpotsInfo_ThenShouldReturnValidMemberSpotResponse() {
        // Given
        Long memberId = 1L;
        int totalPages = 1;
        PageRequest pageable = PageRequest.of(0, 5);

        String email = "test@test.com";
        String socialLoginId = "12345";
        String nickname = "tester";
        String profileImageUrl = "test.jpg";
        Member member = new Member(memberId, email, socialLoginId, nickname, profileImageUrl,
            SocialLoginType.KAKAO, Ages.NONE, Gender.NONE);

        Long spotId = 1L;
        String title = "Test Spot Title";
        double rate = 5.0;
        AttachFile imageFile = new AttachFile(FileType.IMAGE, "test.jpg", "test.jpg", 10L);
        AttachFiles imageFiles = new AttachFiles(Collections.singletonList(imageFile));

        Place mockPlace = mock(Place.class);
        when(mockPlace.getName()).thenReturn("장소명");
        Spot spot = new Spot(spotId, member, null, false, title, null, imageFiles, mockPlace, rate);

        SpotInfo.SpotListInfo spotInfo = new SpotInfo.SpotListInfo(spot);
        List<SpotInfo.SpotListInfo> spotList = Collections.singletonList(spotInfo);

        SpotInfo.MySpotsResponse mockSpotListResponse = new SpotInfo.MySpotsResponse(
            spotList,
            totalPages
        );

        when(spotService.retrieveSpotList(anyLong(), any(Selected.class), any(Pageable.class))).thenReturn(
            mockSpotListResponse);

        // When
        var result = spotFacade.getMemberSpotsInfo(memberId, Selected.PRIVATE, pageable);

        // Then
        assertThat(result).isNotNull()
            .isInstanceOf(SpotInfo.MySpotsResponse.class)
            .usingRecursiveComparison().isEqualTo(mockSpotListResponse);
    }
}
