package kr.co.yigil.file.domain;

import kr.co.yigil.file.AttachFile;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploader {

    AttachFile upload(MultipartFile file);

}
