package kr.co.yigil.file.repository;

import java.util.Optional;
import kr.co.yigil.file.AttachFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachFileRepository extends JpaRepository <AttachFile, Long> {

    Optional<AttachFile> findAttachFileByFileUrl(String url);
}
