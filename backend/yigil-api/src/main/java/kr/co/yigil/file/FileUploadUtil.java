package kr.co.yigil.file;

import java.util.UUID;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.global.exception.FileException;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {

    private static final long MAX_IMAGE_SIZE = 10485760;
    private static final long MAX_VIDEO_SIZE = MAX_IMAGE_SIZE * 5;

    public static AttachFile predictAttachFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String uniqueFileName = generateUniqueFileName(fileName);
        FileType fileType = determineFileType(file);
        String path = getPath(fileType, uniqueFileName);
        long fileSize = file.getSize();
        validateFileSize(fileType, fileSize);
        return new AttachFile(fileType, path, fileName, fileSize);
    }

    public static String generateUniqueFileName(String originalFilename) {
        return UUID.randomUUID() + "_" + originalFilename;
    }

    public static FileType determineFileType(MultipartFile file) {
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

    public static String getPath(FileType fileType, String fileName) {
        String url = fileType == FileType.IMAGE ? "images/" : "videos/";
        return url + fileName;
    }

    private static void validateFileSize(FileType fileType, long size) {
        long maxSize = fileType == FileType.IMAGE ? MAX_IMAGE_SIZE : MAX_VIDEO_SIZE;
        if (size > maxSize) {
            throw new FileException(ExceptionCode.EXCEED_FILE_CAPACITY);
        }
    }
}
