package kr.co.yigil.file;

import java.util.Objects;
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
    private final Consumer<AttachFile> callback;

    public FileUploadEvent(Object source, MultipartFile file, Consumer<AttachFile> callback) {
        super(source);
        this.file = file;
        this.callback = callback;
        this.fileType = determineFileType(file);
        validateFileSize(fileType, file.getSize());
    }

    private void validateFileSize(FileType fileType, long size) {
        long maxSize = fileType == FileType.IMAGE ? MAX_IMAGE_SIZE : MAX_VIDEO_SIZE;
        if (size > maxSize) {
            throw new FileException(ExceptionCode.EXCEED_FILE_CAPACITY);
        }
    }

    private FileType determineFileType(MultipartFile file) {
        /**
         * == null 도 좋지만 Objects.isNull(file.getContentType()) 처럼도 사용 가능하니 참고 하시면 좋을듯 합니다!
         */

        if (file.getContentType() == null) {
            throw new FileException(ExceptionCode.EMPTY_FILE);
        }

        /**
         * Enum에서 해당 경로들을 관리한다면 좀더 간결하지 않을까요?
         * */
        if (file.getContentType().startsWith("image/")) {
            return FileType.IMAGE;
        }

        if (file.getContentType().startsWith("video/")) {
            return FileType.VIDEO;
        }

        throw new FileException(ExceptionCode.INVALID_FILE_TYPE);
    }

}
