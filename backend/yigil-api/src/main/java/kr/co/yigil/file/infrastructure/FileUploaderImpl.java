package kr.co.yigil.file.infrastructure;

import java.util.List;
import kr.co.yigil.file.FileUploadEvent;
import kr.co.yigil.file.FileUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class FileUploaderImpl implements FileUploader {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void upload(MultipartFile file) {
        FileUploadEvent event = new FileUploadEvent(this, file);
        eventPublisher.publishEvent(event);
    }

}
