package kr.co.yigil.travel;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import kr.co.yigil.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE Travel SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
public class Travel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false, length = 20)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    private double rate;

    boolean isDeleted;

    boolean isPrivate;


    protected Travel(Member member, String title, String description, double rate, boolean isPrivate) {
        this.member = member;
        this.title = title;
        this.description = description;
        this.rate = rate;
        createdAt = LocalDateTime.now();
        modifiedAt = LocalDateTime.now();
        this.isPrivate = isPrivate;
    }
    protected Travel(Member member, String title, String description, double rate) {
        this(member, title, description, rate, false);
    }

    public Travel(final Long id,Member member, String title, String description, double rate, boolean isPrivate) {
        this.id = id;
        this.member = member;
        this.title = title;
        this.description = description;
        this.rate = rate;
        createdAt = LocalDateTime.now();
        modifiedAt = LocalDateTime.now();
        this.isPrivate = isPrivate;
    }
    protected Travel(Long id, Member member, String title, String description, double rate) {
        this(id, member, title, description, rate, false);
    }

    public void togglePrivate(){
        this.isPrivate = !isPrivate;
    }
}
