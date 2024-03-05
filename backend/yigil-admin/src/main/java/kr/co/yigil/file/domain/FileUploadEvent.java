package kr.co.yigil.file.domain;

import java.util.function.Consumer;
import kr.co.yigil.file.FileType;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.global.exception.FileException;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class FileUploadEvent extends ApplicationEvent {

    private static final long MAX_IMAGE_SIZE = 10485760;
    private static final long MAX_VIDEO_SIZE = MAX_IMAGE_SIZE * 5;

    private final MultipartFile file;
    private final kr.co.yigil.file.FileType fileType;
    private final Consumer<String> callback;

    public FileUploadEvent(Object source, MultipartFile file, Consumer<String> callback) {
        super(source);
        this.file = file;
        this.callback = callback;
        fileType = determineFileType(file);
        validateFileSize(fileType, file.getSize());
    }

    private void validateFileSize(kr.co.yigil.file.FileType fileType, long size) {
        long maxSize = fileType == kr.co.yigil.file.FileType.IMAGE ? MAX_IMAGE_SIZE : MAX_VIDEO_SIZE;
        if (size > maxSize) {
            throw new FileException(ExceptionCode.EXCEED_FILE_CAPACITY);
        }
    }

    private kr.co.yigil.file.FileType determineFileType(MultipartFile file) {
        if (file.getContentType() == null) throw new FileException(ExceptionCode.EMPTY_FILE);

        if(file.getContentType().startsWith("image/")) {
            return kr.co.yigil.file.FileType.IMAGE;
        }

        if(file.getContentType().startsWith("video/")) {
            return FileType.VIDEO;
        }

        throw new FileException(ExceptionCode.INVALID_FILE_TYPE);
    }

}
