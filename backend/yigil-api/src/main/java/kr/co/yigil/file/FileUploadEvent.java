package kr.co.yigil.file;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import kr.co.yigil.File.AttachFiles;
import kr.co.yigil.File.FileType;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.global.exception.FileException;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class FileUploadEvent extends ApplicationEvent {

    private static final long MAX_IMAGE_SIZE = 10485760;
    private static final long MAX_VIDEO_SIZE = MAX_IMAGE_SIZE * 5;

    private List<MultipartFile> files;
    private List<FileType> fileTypes = new ArrayList<>();
    private Consumer<AttachFiles> callback;

    public FileUploadEvent(Object source, List<MultipartFile> files, Consumer<AttachFiles> callback) {
        super(source);
        this.files = files;
        this.callback = callback;

        files.forEach(file -> {
            FileType fileType = determineFileType(file);
            validateFileSize(fileType, file.getSize());
            fileTypes.add(fileType);
        });
    }

    private void validateFileSize(FileType fileType, long size) {
        long maxSize = fileType == FileType.IMAGE ? MAX_IMAGE_SIZE : MAX_VIDEO_SIZE;
        if (size > maxSize) {
            throw new FileException(ExceptionCode.EXCEED_FILE_CAPACITY);
        }
    }

    private FileType determineFileType(MultipartFile file) {
        if (file.getContentType() == null) {
            throw new FileException(ExceptionCode.EMPTY_FILE);
        }

        if (file.getContentType().startsWith("image/")) {
            return FileType.IMAGE;
        }

        if (file.getContentType().startsWith("video/")) {
            return FileType.VIDEO;
        }

        throw new FileException(ExceptionCode.INVALID_FILE_TYPE);
    }

}
