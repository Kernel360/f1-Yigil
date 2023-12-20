package kr.co.yigil.file;

import static kr.co.yigil.global.exception.ExceptionCode.EMPTY_FILE;
import static kr.co.yigil.global.exception.ExceptionCode.EXCEED_FILE_CAPACITY;
import static kr.co.yigil.global.exception.ExceptionCode.INVALID_FILE_TYPE;

import java.util.function.Consumer;
import kr.co.yigil.global.exception.FileException;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class FileUploadEvent extends ApplicationEvent {
    private static final long MAX_IMAGE_SIZE = 10485760;
    private static final long MAX_VIDEO_SIZE = MAX_IMAGE_SIZE * 5;

    private MultipartFile file;
    private FileType fileType;
    private Consumer<String> callback;

    public FileUploadEvent(Object source, MultipartFile file, Consumer<String> callback) {
        super(source);
        this.file = file;
        this.callback = callback;
        fileType = determineFileType(file);
        validateFileSize(fileType, file.getSize());
    }

    private void validateFileSize(FileType fileType, long size) {
        long maxSize = fileType == FileType.IMAGE ? MAX_IMAGE_SIZE : MAX_VIDEO_SIZE;
        if(size > maxSize) throw new FileException(EXCEED_FILE_CAPACITY);
    }

    private FileType determineFileType(MultipartFile file) {
        if (file.getContentType() == null) throw new FileException(EMPTY_FILE);

        if(file.getContentType().startsWith("image/")) {
            return FileType.IMAGE;
        }

        if(file.getContentType().startsWith("video/")) {
            return FileType.VIDEO;
        }

        throw new FileException(INVALID_FILE_TYPE);
     }
}
