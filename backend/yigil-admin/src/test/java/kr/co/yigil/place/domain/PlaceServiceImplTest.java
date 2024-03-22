package kr.co.yigil.place.domain;

import java.time.LocalDateTime;
import kr.co.yigil.file.AttachFile;
import kr.co.yigil.file.domain.FileUploader;
import kr.co.yigil.place.domain.PlaceCommand.PlaceMapCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Point;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import org.springframework.security.core.parameters.P;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PlaceServiceImplTest {

    @Mock
    private PlaceReader placeReader;
    @Mock
    private FileUploader fileUploader;
    @InjectMocks
    private PlaceServiceImpl placeService;


    @DisplayName("장소 목록 조회")
    @Test
    void whenGetPlaces_thenShouldReturnPlaceInfoList() {

        PlaceMapCommand command = new PlaceMapCommand(1, 1, 1, 1);
        Point mockPoint = mock(Point.class);
        AttachFile mockAttachFile = mock(AttachFile.class);
        Place mockPlace = new Place(1L, "name", "address", mockPoint, mockAttachFile, mockAttachFile,
                LocalDateTime.now());
        when(placeReader.getPlaces(1, 1, 1, 1)).thenReturn(List.of(mockPlace));

        var result = placeService.getPlaces(command);
        verify(placeReader).getPlaces(1, 1, 1, 1);
    }

    @DisplayName("장소 상세 조회")
    @Test
    void getPlaceDetail() {
        Place mockPlace = mock(Place.class);
        when(placeReader.getPlace(any(Long.class))).thenReturn(mockPlace);

        var result = placeService.getPlaceDetail(1L);
        assertEquals(mockPlace, result);
    }

    @Test
    void updateImage() {
        PlaceCommand.UpdateImageCommand command = new PlaceCommand.UpdateImageCommand(1L, null);

        Place mockPlace = mock(Place.class);
        AttachFile mockAttachFile = mock(AttachFile.class);

        when(placeReader.getPlace(any(Long.class))).thenReturn(mockPlace);
        when(fileUploader.upload(any())).thenReturn(mockAttachFile);

        placeService.updateImage(command);

        verify(mockPlace).updateImage(mockAttachFile);
    }
}