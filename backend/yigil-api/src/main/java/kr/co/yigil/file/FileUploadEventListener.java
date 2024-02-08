package kr.co.yigil.file;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileUploadEventListener {

    private final AmazonS3Client amazonS3Client;

    private final String bucketName = "cdn.yigil.co.kr";

    @Async
    @EventListener
    public Future<AttachFile> handleFileUpload(FileUploadEvent event) throws IOException {
        MultipartFile file = event.getFile();
        FileType fileType = event.getFileType();
        String fileName = generateUniqueFileName(file.getOriginalFilename());
        String s3Path = getS3Path(fileType, fileName);

        // 각자가 하는 역할을 메서드로 구분하면 좋을것 같습니다.
        AttachFile attachFile = new AttachFile(fileType, s3Path, file.getOriginalFilename(),
                file.getSize());

        // 각자가 하는 역할을 메서드로 구분하면 좋을것 같습니다.
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        metadata.setContentDisposition("inline");
        amazonS3Client.putObject(bucketName, s3Path, file.getInputStream(), metadata);
        event.getCallback().accept(attachFile);

        return CompletableFuture.completedFuture(attachFile);
    }

    private String getS3Path(FileType fileType, String fileName) {
        /**
         * 해당 경로들은 FileType.IMAGE, FileType.VIDEO 의 변수로 넣어주는것은 어떨까요??
         */

        String url = fileType == FileType.IMAGE ? "images/" : "videos/";
        return url + fileName;
    }

    private String generateUniqueFileName(String originalFilename) {
        return UUID.randomUUID() + "_" + originalFilename;
    }
}
