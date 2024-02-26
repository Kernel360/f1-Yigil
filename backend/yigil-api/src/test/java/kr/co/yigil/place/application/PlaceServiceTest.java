//package kr.co.yigil.place.application;
//
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.never;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.util.List;
//import java.util.Optional;
//import kr.co.yigil.file.AttachFile;
//import kr.co.yigil.file.FileType;
//import kr.co.yigil.place.Place;
//import kr.co.yigil.place.domain.PlaceRate;
//import kr.co.yigil.place.dto.response.PlaceFindDto;
//import kr.co.yigil.place.dto.response.PlaceInfoResponse;
//import kr.co.yigil.place.repository.PlaceRepository;
//import kr.co.yigil.travel.domain.spot.SpotCount;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.locationtech.jts.geom.Coordinate;
//import org.locationtech.jts.geom.GeometryFactory;
//import org.locationtech.jts.geom.Point;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Slice;
//import org.springframework.data.domain.SliceImpl;
//
//@ExtendWith(MockitoExtension.class)
//public class PlaceServiceTest {
//    @InjectMocks
//    private PlaceService placeService;
//    @Mock
//    private PlaceRepository placeRepository;
//    @Mock
//    private SpotRedisIntegrityService spotRedisIntegrityService;
//    @Mock
//    private PlaceRateRedisIntegrityService placeRateRedisIntegrityService;
//
//    public PlaceServiceTest() {
//    }
//
//    @DisplayName("getPlaceInfo 메서드가 유효한 인자를 넘겨받았을 때 올바른 응답을 내리는지")
//    @Test
//    void GivenValidPlaceId_WhenGetPlaceInfoTest_ThenReturnPlaceFindResponse(){
//
//        Long placeId = 1L;
//        GeometryFactory geometryFactory = new GeometryFactory();
//        Point mockPoint = geometryFactory.createPoint(new Coordinate(0, 0));
//        AttachFile mockAttachFile = new AttachFile(FileType.IMAGE, "img.url", "original.name", 10L );
//        Place mockPlace = new Place("패스트캠퍼스", "봉은사역 근처", mockPoint, mockAttachFile, mockAttachFile);
//        when(placeRepository.findById(placeId)).thenReturn(Optional.of(mockPlace));
//        when(placeRateRedisIntegrityService.ensurePlaceRate(placeId)).thenReturn(new PlaceRate(placeId, 4.0));
//        when(spotRedisIntegrityService.ensureSpotCounts(placeId)).thenReturn(new SpotCount(placeId, 10));
//
//        PlaceInfoResponse response = placeService.getPlaceInfo(placeId);
//
//        assertThat(response).isNotNull();
//        assertThat(response).isInstanceOf(PlaceInfoResponse.class);
//    }
//
//    @DisplayName("getOrCreatePlace 메서드가 존재하는 place의 placeName , placeAddress 인자를 받았을 때")
//    @Test
//    public void getOrCreatePlaceExistingTest() {
//        String placeName = "패스트캠퍼스";
//        String placeAddress = "봉은사역 근처";
//        String placePointJson = "{\"type\":\"Point\",\"coordinates\":[127.123456,37.123456]}";
//        String placeImgUrl = "placeImgUrl";
//        AttachFile mapStaticImageFile = new AttachFile(FileType.IMAGE, "img.url", "original.name", 10L );
//        GeometryFactory geometryFactory = new GeometryFactory();
//        Point mockPoint = geometryFactory.createPoint(new Coordinate(0, 0));
//        Place mockPlace = new Place(placeName, placeAddress, mockPoint, mapStaticImageFile, mapStaticImageFile);
//        when(placeRepository.findByNameAndAddress(placeName, placeAddress)).thenReturn(Optional.of(mockPlace));
//
//        Place resultPlace = placeService.getOrCreatePlace(placeName, placeAddress, placePointJson, mapStaticImageFile, mapStaticImageFile);
//
//        assertThat(resultPlace).isEqualTo(mockPlace);
//        verify(placeRepository, never()).save(any(Place.class));
//
//    }
//
//    @DisplayName("getOrCreatePlace 메서드가 존재하지 않는 place의 placeName , placeAddress 인자를 받았을 때 ")
//    @Test
//    public void getOrCreatePlaceNonExistingTest() {
//
//        String placeName = "패스트캠퍼스";
//        String placeAddress = "봉은사역 근처";
//        String placePointJson = "{\"type\":\"Point\",\"coordinates\":[127.123456,37.123456]}";
//
//        GeometryFactory geometryFactory = new GeometryFactory();
//        Point mockPoint = geometryFactory.createPoint(new Coordinate(0, 0));
//        AttachFile mockAttachFile = new AttachFile(FileType.IMAGE, "img.url", "original.name", 10L );
//        Place savedPlace = new Place(1L, placeName, placeAddress, mockPoint,  mockAttachFile, mockAttachFile);
//
//        when(placeRepository.findByNameAndAddress(placeName, placeAddress)).thenReturn(Optional.empty());
//
//        when(placeRepository.save(any(Place.class))).thenReturn(savedPlace);
//
//        Place resultPlace = placeService.getOrCreatePlace(placeName, placeAddress, placePointJson, mockAttachFile, mockAttachFile);
//
//        assertThat(resultPlace).isEqualTo(savedPlace);
//    }
//
//    @DisplayName("getPlaceList 메서드가 유효한 인자를 넘겨받았을 때 올바른 응답을 내리는지")
//    @Test
//    void GivenValidPageRequest_WhenGetPlaceListTest_ThenReturnPlaceFindDto(){
//        long placeId = 1L;
//        GeometryFactory geometryFactory = new GeometryFactory();
//        Point mockPoint = geometryFactory.createPoint(new Coordinate(0, 0));
//        AttachFile mockAttachFile = new AttachFile(FileType.IMAGE, "img.url", "original.name", 10L );
//        Place mockPlace = new Place(placeId, "패스트캠퍼스", "봉은사역 근처", mockPoint, mockAttachFile, mockAttachFile);
//        when(placeRepository.findAllPlaces(any(Pageable.class))).thenReturn(
//                new SliceImpl<>(List.of(mockPlace)));
//        when(placeRateRedisIntegrityService.ensurePlaceRate(anyLong())).thenReturn(new PlaceRate(placeId, 4.0));
//        when(spotRedisIntegrityService.ensureSpotCounts(anyLong())).thenReturn(new SpotCount(placeId, 10));
//
//        Slice<PlaceFindDto> response = placeService.getPlaceList(Pageable.unpaged());
//
//        PlaceFindDto placeFindDto = response.getContent().get(0);
//        assertThat(placeFindDto.getName()).isEqualTo(mockPlace.getName());
//    }
//
//
//
//}
