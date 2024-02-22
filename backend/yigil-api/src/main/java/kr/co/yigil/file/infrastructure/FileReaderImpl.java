package kr.co.yigil.file.infrastructure;

import kr.co.yigil.file.AttachFile;
import kr.co.yigil.file.FileReader;
import kr.co.yigil.file.repository.AttachFileRepository;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileReaderImpl implements FileReader {

    private final AttachFileRepository attachFileRepository;

    @Override
    public AttachFile getFileByUrl(String url) {
        return attachFileRepository.findAttachFileByFileUrl(url)
                .orElseThrow(() -> new BadRequestException(ExceptionCode.INVALID_FILE_URL));
    }
}
