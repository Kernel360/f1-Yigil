package kr.co.yigil.notice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String author;

    private String title;

    private String content;

//    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
//    private AttachFile attachFile;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;
    public Notice (String author, String title, String content){
        this.author = author;
        this.title = title;
        this.content = content;
//        this.attachFile = attachFile;
        createdAt = LocalDateTime.now();
        modifiedAt = LocalDateTime.now();
    }

    public void updateNotice(Notice entity) {
        this.author = entity.getAuthor();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.modifiedAt = LocalDateTime.now();
    }
}
