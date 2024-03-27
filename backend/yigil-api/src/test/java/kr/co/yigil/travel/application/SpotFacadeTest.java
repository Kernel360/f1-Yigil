package kr.co.yigil.travel.application;

import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.file.FileUploader;
import kr.co.yigil.global.Selected;
import kr.co.yigil.travel.domain.dto.SpotListDto;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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
        Long placeId = 1L;
        Accessor mockAccessor = mock(Accessor.class);
        SpotInfo.Slice mockSlice = mock(SpotInfo.Slice.class);
        Pageable pageable = PageRequest.of(0, 5);
        when(spotService.getSpotSliceInPlace(eq(placeId), any(Accessor.class), any(Pageable.class))).thenReturn(mockSlice);

        SpotInfo.Slice result = spotFacade.getSpotSliceInPlace(placeId, mockAccessor, pageable);

        assertEquals(mockSlice, result);
        verify(spotService).getSpotSliceInPlace(placeId, mockAccessor, pageable);
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

        Long spotId = 1L;
        String title = "Test Spot Title";
        double rate = 5.0;
        Long placeId = 1L;
        String imageUrl = "test.jpg";


        SpotListDto spot = new SpotListDto(spotId, placeId, title, rate, imageUrl,
            LocalDateTime.now(), false);

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

    @DisplayName("getFavoriteSpotsInfo 메서드가 잘 동작하는지")
    @Test
    void getFavoriteSpotsInfo() {
        Long memberId = 1L;
        PageRequest pageRequest = PageRequest.of(0, 5);
        SpotInfo.MyFavoriteSpotsInfo mockFavoriteSpotsInfo = mock(SpotInfo.MyFavoriteSpotsInfo.class);

        when(spotService.getFavoriteSpotsInfo(memberId, pageRequest)).thenReturn(mockFavoriteSpotsInfo);

        SpotInfo.MyFavoriteSpotsInfo result = spotFacade.getFavoriteSpotsInfo(memberId, pageRequest);

        assertEquals(mockFavoriteSpotsInfo, result);
        verify(spotService).getFavoriteSpotsInfo(memberId, pageRequest);
    }
}
