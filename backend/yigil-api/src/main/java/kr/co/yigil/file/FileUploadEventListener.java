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

    private final String bucketName = "cdn.yigil.co.kr";

    @Async
    @EventListener
    public Future<kr.co.yigil.file.AttachFile> handleFileUpload(FileUploadEvent event) throws IOException {
        MultipartFile file = event.getFile();
        kr.co.yigil.file.FileType fileType = event.getFileType();
        String fileName = generateUniqueFileName(file.getOriginalFilename());
        String s3Path = getS3Path(fileType, fileName);

        kr.co.yigil.file.AttachFile attachFile = new kr.co.yigil.file.AttachFile(fileType, s3Path, file.getOriginalFilename(),
                file.getSize());

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        amazonS3Client.putObject(bucketName, s3Path, file.getInputStream(), metadata);
        event.getCallback().accept(attachFile);

        return CompletableFuture.completedFuture(attachFile);
    }

    private String getS3Path(kr.co.yigil.file.FileType fileType, String fileName) {
        String url = fileType == kr.co.yigil.file.FileType.IMAGE ? "images/" : "videos/";
        return url + fileName;
    }

    private String generateUniqueFileName(String originalFilename) {
        return UUID.randomUUID() + "_" + originalFilename;
    }
}
