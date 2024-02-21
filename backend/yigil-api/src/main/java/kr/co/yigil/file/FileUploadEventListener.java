package kr.co.yigil.file;

import static kr.co.yigil.file.FileUploadUtil.generateUniqueFileName;
import static kr.co.yigil.file.FileUploadUtil.getPath;

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
    public void handleFileUpload(FileUploadEvent event) throws IOException {
        MultipartFile file = event.getFile();
        FileType fileType = event.getFileType();
        String fileName = generateUniqueFileName(file.getOriginalFilename());
        String s3Path = getPath(fileType, fileName);

        AttachFile attachFile = new AttachFile(fileType, s3Path, file.getOriginalFilename(),
                file.getSize());

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        metadata.setContentDisposition("inline");
        amazonS3Client.putObject(bucketName, s3Path, file.getInputStream(), metadata);
    }

}
