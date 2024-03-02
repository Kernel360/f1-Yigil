package kr.co.yigil.file;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AttachFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @NotNull
    private FileType fileType;

    @NotNull
    private String fileUrl;

    @NotNull
    private String originalFileName;

    @NotNull
    private Long fileSize;

    public AttachFile(final FileType fileType, final String fileUrl, final String originalFileName, final Long fileSize) {
        this.fileType = fileType;
        this.fileUrl = fileUrl;
        this.originalFileName = originalFileName;
        this.fileSize = fileSize;
    }

    public String getFileUrl() {
        return "http://cdn.yigil.co.kr/" + fileUrl;
    }
}
