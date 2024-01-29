package kr.co.yigil.file;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
    public Future<AttachFiles> handleFileUpload(FileUploadEvent event) throws IOException {
        List<MultipartFile> files = event.getFiles();
        List<FileType> fileTypes = event.getFileTypes();

        AttachFiles attachFiles = new AttachFiles(new ArrayList<>());

        for(int i=0; i<files.size(); i++) {
            MultipartFile file = files.get(i);
            FileType fileType = fileTypes.get(i);

            String fileName = generateUniqueFileName(file.getOriginalFilename());
            String s3Path = getS3Path(fileType, fileName);

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            amazonS3Client.putObject(bucketName, s3Path, file.getInputStream(), metadata);

            AttachFile attachFile = new AttachFile(FileType.IMAGE, s3Path, file.getOriginalFilename(),
                    file.getSize());
            attachFiles.addFile(attachFile);
        }
        event.getCallback().accept(attachFiles);

        return CompletableFuture.completedFuture(attachFiles);
    }

    private String getS3Path(FileType fileType, String fileName) {
        String url = fileType == FileType.IMAGE ? "images/" : "videos/";
        return url + fileName;
    }

    private String generateUniqueFileName(String originalFilename) {
        return UUID.randomUUID() + "_" + originalFilename;
    }
}
