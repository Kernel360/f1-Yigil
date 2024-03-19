package kr.co.yigil.place.domain;

import kr.co.yigil.file.AttachFile;
import kr.co.yigil.file.domain.FileUploader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

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
    void whenGetPlaces_thenShouldReturnPlacePage() {

        PageRequest pageRequest = PageRequest.of(0, 10);
        when(placeReader.getPlaces(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(new Place())));

        var result = placeService.getPlaces(pageRequest);

        assertThat(result).isNotNull().isInstanceOf(PageImpl.class);
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
        PlaceCommand.UpdateImageCommand command = new PlaceCommand.UpdateImageCommand();
        command.setPlaceId(1L);
        command.setImageFile(null);

        Place mockPlace = mock(Place.class);
        AttachFile mockAttachFile = mock(AttachFile.class);

        when(placeReader.getPlace(any(Long.class))).thenReturn(mockPlace);
        when(fileUploader.upload(any())).thenReturn(mockAttachFile);

        placeService.updateImage(command);

        verify(mockPlace).updateImage(mockAttachFile);
    }
}