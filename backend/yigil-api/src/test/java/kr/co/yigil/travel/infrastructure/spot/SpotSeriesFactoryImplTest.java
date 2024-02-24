package kr.co.yigil.travel.infrastructure.spot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import kr.co.yigil.file.AttachFile;
import kr.co.yigil.file.FileReader;
import kr.co.yigil.file.FileUploadUtil;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.domain.spot.SpotCommand;
import kr.co.yigil.travel.domain.spot.SpotCommand.OriginalSpotImage;
import kr.co.yigil.travel.domain.spot.SpotCommand.UpdateSpotImage;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

@ExtendWith(MockitoExtension.class)
public class SpotSeriesFactoryImplTest {

    @Mock
    private FileReader fileReader;

    private static MockedStatic<FileUploadUtil> fileUploadUtil;

    @InjectMocks
    private SpotSeriesFactoryImpl spotSeriesFactory;

    @BeforeAll
    public static void beforeAll() {
        fileUploadUtil = mockStatic(FileUploadUtil.class);
    }

    @Test
    void modify_UpdatesSpotSuccessfully() {
        Spot spot = mock(Spot.class);
        double newRate = 4.5;
        String newDescription = "Updated description";
        List<OriginalSpotImage> originalImages = Arrays.asList(
                new SpotCommand.OriginalSpotImage("originalUrl1", 1),
                new SpotCommand.OriginalSpotImage("originalUrl2", 0));
        List<SpotCommand.UpdateSpotImage> updatedImages = List.of(
                new UpdateSpotImage(
                        new MockMultipartFile("newImage1", "newImage1.jpg", "image/jpeg",
                                new byte[10]), 2));

        AttachFile attachFile = mock(AttachFile.class);

        when(fileReader.getFileByUrl(anyString())).thenReturn(attachFile);
        when(FileUploadUtil.predictAttachFile(any())).thenReturn(attachFile);

        SpotCommand.ModifySpotRequest command = SpotCommand.ModifySpotRequest.builder()
                .rate(newRate)
                .description(newDescription)
                .originalImages(originalImages)
                .updatedImages(updatedImages)
                .build();

        Spot modifiedSpot = spotSeriesFactory.modify(command, spot);

        assertNotNull(modifiedSpot);
    }

    @AfterAll
    public static void afterAll() {
        fileUploadUtil.close();
    }
}
