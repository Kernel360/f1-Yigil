package kr.co.yigil.file.infrastructure;

import java.util.concurrent.CompletableFuture;
import kr.co.yigil.file.AttachFile;
import kr.co.yigil.file.FileType;
import kr.co.yigil.file.domain.FileUploadEvent;
import kr.co.yigil.file.domain.FileUploader;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.global.exception.FileException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
@Component
@RequiredArgsConstructor
public class FileUploaderImpl implements FileUploader {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public AttachFile upload(MultipartFile file) {
        CompletableFuture<String> fileUploadResult = new CompletableFuture<>();

        FileUploadEvent event = new FileUploadEvent(this, file, fileUploadResult::complete);
        eventPublisher.publishEvent(event);

        String fileUrl = fileUploadResult.join();
        FileType fileType = determineFileType(file);

        return new AttachFile(fileType, fileUrl, file.getOriginalFilename(), file.getSize());
    }

    private FileType determineFileType(MultipartFile file) {
        if (file == null) throw new FileException(ExceptionCode.EMPTY_FILE);

        if(file.getContentType().startsWith("image/")) {
            return FileType.IMAGE;
        }

        if(file.getContentType().startsWith("video/")) {
            return FileType.VIDEO;
        }

        throw new FileException(ExceptionCode.INVALID_FILE_TYPE);
    }
}
