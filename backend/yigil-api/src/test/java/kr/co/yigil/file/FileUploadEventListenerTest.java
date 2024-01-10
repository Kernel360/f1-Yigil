package kr.co.yigil.file;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;
import java.util.function.Consumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadEventListenerTest {

    @Mock
    private AmazonS3Client mockAmazonS3Client;

    @InjectMocks
    private FileUploadEventListener fileUploadEventListener;

    @Captor
    private ArgumentCaptor<PutObjectRequest> putObjectRequestCaptor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("파일 업로드가 S3로 잘 동작하는지")
    @Test
    void shouldUploadFileToS3() throws IOException {
        MultipartFile mockFile = new MockMultipartFile("file", "test.jpg", "image/jpeg", new byte[10]);
        FileUploadEvent event = new FileUploadEvent(new Object(), mockFile, mock(Consumer.class));
        fileUploadEventListener.handleFileUpload(event);

        verify(mockAmazonS3Client).putObject(eq("yigilbucket"), anyString(), any(), any(
                ObjectMetadata.class));
    }

}
