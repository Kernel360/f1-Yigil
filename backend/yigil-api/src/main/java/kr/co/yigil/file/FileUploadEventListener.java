package kr.co.yigil.file;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileUploadEventListener {

    private final AmazonS3Client amazonS3Client;
    private String bucketName = "yigilbucket";

    @Async
    @EventListener
    public Future<String> handleFileUpload(FileUploadEvent event) throws IOException {
        MultipartFile file = event.getFile();
        FileType fileType = event.getFileType();
        String fileName = generateUniqueFileName(file.getOriginalFilename());
        String s3Path = getS3Path(fileType, fileName);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        amazonS3Client.putObject(bucketName, s3Path, file.getInputStream(), metadata);
        event.getCallback().accept(s3Path);
        return CompletableFuture.completedFuture(s3Path);
    }

    private String getS3Path(FileType fileType, String fileName) {
        String url = fileType == FileType.IMAGE ? "images/" : "videos/";
        return url + fileName;
    }

    private String generateUniqueFileName(String originalFilename) {
        return UUID.randomUUID() + "_" + originalFilename;
    }
}
