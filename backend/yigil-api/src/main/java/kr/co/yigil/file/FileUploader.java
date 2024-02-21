package kr.co.yigil.file;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploader {
    void upload(MultipartFile file);

}
