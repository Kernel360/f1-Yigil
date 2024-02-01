package kr.co.yigil.file;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import java.util.function.Consumer;
import kr.co.yigil.global.exception.FileException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadEventTest {

    @DisplayName("이미지 파일을 잘 구분하는지")
    @Test
    void shouldDetermineFileTypeAsImage() {
        MultipartFile file = new MockMultipartFile("image", "image.jpg", "image/jpeg", new byte[10]);
        FileUploadEvent event = new FileUploadEvent(new Object(), file,
                (java.util.function.Consumer<AttachFile>) mock(Consumer.class));
        assertEquals(FileType.IMAGE, event.getFileType());
    }

    @DisplayName("파일 크기가 너무 클 때 FileException 잘 발생하는지")
    @Test
    void shouldThrowExceptionForExceedingFileSize() {
        byte[] largeFileContent = new byte[10485761];
        MultipartFile largeFile = new MockMultipartFile("image", "image.jpg", "image/jpeg", largeFileContent);

        assertThrows(FileException.class, () -> new FileUploadEvent(new Object(), largeFile, mock(
                Consumer.class)));
    }

    @DisplayName("validFile이 유효한 파일을 잘 구별하는지")
    @Test
    void shouldProcessValidFile() {
        MultipartFile file = new MockMultipartFile("video", "video.mp4", "video/mp4", new byte[10]);
        assertDoesNotThrow(() -> new FileUploadEvent(new Object(), file, mock(Consumer.class)));
    }

    @DisplayName("빈 파일이 들어왔을 때 FileException이 잘 발생하는지")
    @Test
    void shouldThrowExceptionForEmptyFileType() {
        MultipartFile file = new MockMultipartFile("empty", "", "", new byte[0]);
        assertThrows(FileException.class, () -> new FileUploadEvent(new Object(), file, mock(
                Consumer.class)));
    }
}
