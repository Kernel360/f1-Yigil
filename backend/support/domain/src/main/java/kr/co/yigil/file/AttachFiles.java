package kr.co.yigil.file;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AttachFiles {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "travel_id")
    private List<AttachFile> files = new LinkedList<>();

    public AttachFiles(final List<AttachFile> files) {
        this.files = files;
    }

    public List<AttachFile> getFiles() {
        return Collections.unmodifiableList(files);
    }

    public void updateFiles(List<AttachFile> newAttachFiles) {
        this.files.clear();
        this.files.addAll(newAttachFiles);
    }

    public AttachFile getRepresentativeFile() {
        if (files.isEmpty()) {
            return null;
        }
        return files.get(0);
    }

    public void addFile(AttachFile file) {
        files.add(file);
        validateFilesSize();
    }

    private void validateFilesSize() {
        if (files.size() > 5) {
            throw new IllegalArgumentException("files length must not over 5");
        }
    }

    public List<String> getUrls() {
        List<String> urls = new ArrayList<>();
        for (AttachFile file : files) {
            urls.add("http://cdn.yigi.co.kr/" + file.getFileUrl());
        }
        return urls;
    }

    public Optional<AttachFile> findByUrl(String url) {
        return files.stream()
                .filter(file -> file.getFileUrl().equals(url))
                .findFirst();
    }
}
