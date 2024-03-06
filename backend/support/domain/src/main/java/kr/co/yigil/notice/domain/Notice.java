package kr.co.yigil.notice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import kr.co.yigil.admin.domain.Admin;
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

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @JoinColumn(name = "author_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Admin author;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;
    public Notice (Admin author, String title, String content){
        this.author = author;
        this.title = title;
        this.content = content;
        createdAt = LocalDateTime.now();
        modifiedAt = LocalDateTime.now();
    }

    public void updateNotice(String title, String content){
        this.title = title;
        this.content = content;
        modifiedAt = LocalDateTime.now();
    }

    public String getAuthorProfileImage(){
        if(author.getProfileImage() == null){
            return null;
        }
        return author.getProfileImage().getFileUrl();
    }
}
