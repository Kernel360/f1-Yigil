package kr.co.yigil.place.application;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;
import kr.co.yigil.file.AttachFile;
import kr.co.yigil.file.FileType;
import kr.co.yigil.place.Place;
import kr.co.yigil.place.dto.response.PlaceInfoResponse;
import kr.co.yigil.place.repository.PlaceRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PlaceServiceTest {
    @InjectMocks
    private PlaceService placeService;
    @Mock
    private PlaceRepository placeRepository;

    @DisplayName("")
    @Test
    void GivenValidPlaceId_WhenGetPlaceTest_ThenReturnPlaceFindResponse(){

        Long placeId = 1L;
        GeometryFactory geometryFactory = new GeometryFactory();
        Point mockPoint = geometryFactory.createPoint(new Coordinate(0, 0));
        AttachFile mockAttachFile = new AttachFile(FileType.IMAGE, "img.url", "original.name", 10L );
        Place mockPlace = new Place("패스트캠퍼스", "봉은사역 근처", mockPoint, "FastFive01.jpg", mockAttachFile);
        when(placeRepository.findById(placeId)).thenReturn(Optional.of(mockPlace));
        PlaceInfoResponse response = placeService.getPlaceInfo(placeId);

        assertThat(response).isNotNull();
        assertThat(response).isInstanceOf(PlaceInfoResponse.class);
    }

    @DisplayName("getOrCreatePlace 메서드가 존재하는 placeName 인자를 받았을 때 ")
    @Test
    public void getOrCreatePlaceExistingTest() {
        String placeName = "패스트캠퍼스";
        String placeAddress = "봉은사역 근처";
        String placePointJson = "Test PointJson";

        GeometryFactory geometryFactory = new GeometryFactory();
        Point mockPoint = geometryFactory.createPoint(new Coordinate(0, 0));
        AttachFile mockAttachFile = new AttachFile(FileType.IMAGE, "img.url", "original.name", 10L );
        Place mockPlace = new Place("패스트캠퍼스", "봉은사역 근처", mockPoint, "FastFive01.jpg", mockAttachFile);
        when(placeRepository.findByName(placeName)).thenReturn(Optional.of(mockPlace));

        Place resultPlace = placeService.getOrCreatePlace(placeName, placeAddress, placePointJson, "placeImgUrl", mockAttachFile);

        assertThat(resultPlace).isEqualTo(mockPlace);
    }

    @DisplayName("Given non-existing placeName, when getOrCreatePlace, then return new Place")
    @Test
    public void getOrCreatePlaceNonExistingTest() {

        String placeName = "패스트캠퍼스";
        String placeAddress = "봉은사역 근처";
        String placePointJson = "{\"type\":\"Point\",\"coordinates\":[127.123456,37.123456]}";

        GeometryFactory geometryFactory = new GeometryFactory();
        Point mockPoint = geometryFactory.createPoint(new Coordinate(0, 0));
        AttachFile mockAttachFile = new AttachFile(FileType.IMAGE, "img.url", "original.name", 10L );
        Place savedPlace = new Place(1L, placeName, placeAddress, mockPoint,  "FastFive01.jpg", mockAttachFile);

        when(placeRepository.findByName(placeName)).thenReturn(Optional.empty());

        when(placeRepository.save(ArgumentMatchers.any(Place.class))).thenReturn(savedPlace);

        Place resultPlace = placeService.getOrCreatePlace(placeName, placeAddress, placePointJson, "placeImgUrl", mockAttachFile);

        assertThat(resultPlace).isEqualTo(savedPlace);
    }


}
