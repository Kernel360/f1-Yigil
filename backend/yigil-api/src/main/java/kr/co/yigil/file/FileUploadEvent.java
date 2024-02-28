package kr.co.yigil.file;

import static kr.co.yigil.file.FileUploadUtil.determineFileType;

import java.util.function.Consumer;
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
    private final FileType fileType;
    private final String fileName;

    public FileUploadEvent(Object source, MultipartFile file, String fileName) {
        super(source);
        this.file = file;
        this.fileType = determineFileType(file);
        this.fileName = fileName;
        validateFileSize(fileType, file.getSize());
    }

    private void validateFileSize(FileType fileType, long size) {
        long maxSize = fileType == FileType.IMAGE ? MAX_IMAGE_SIZE : MAX_VIDEO_SIZE;
        if (size > maxSize) {
            throw new FileException(ExceptionCode.EXCEED_FILE_CAPACITY);
        }
    }

}
