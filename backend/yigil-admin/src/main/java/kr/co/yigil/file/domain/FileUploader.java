package kr.co.yigil.file.domain;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploader {

    AdminAttachFile upload(MultipartFile file);

}
