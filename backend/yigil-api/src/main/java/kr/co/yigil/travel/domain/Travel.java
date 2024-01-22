package kr.co.yigil.travel.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import java.time.LocalDateTime;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
@Getter
@SQLDelete(sql = "UPDATE Travel SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
public class Travel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    boolean isDeleted;

    protected Travel() {
        createdAt = LocalDateTime.now();
        modifiedAt = LocalDateTime.now();
    }

    public Travel(final Long id) {
        this.id = id;
        createdAt = LocalDateTime.now();
        modifiedAt = LocalDateTime.now();
    }

    protected void setId(Long id) {
        this.id = id;
    }
}
