package kr.co.yigil.comment.domain;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import kr.co.yigil.member.Member;
import kr.co.yigil.travel.Travel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Getter
@SQLDelete(sql = "UPDATE Comment SET is_deleted = true WHERE id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "travel_id")
    private Travel travel;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    boolean isDeleted;

    /**
     * 생성자가 의미하는 내용을 한번에 보기 어렵습니다.
     * this를 통해 1차적으로 깔끔하게 할 수 있을것 같습니다.
     *
     * 정적 펙토리 메서드도 한번 확인해보시면 좋을것 같습니다.
     */
    public Comment(String content, Member member, Travel travel) {
        this.content = content;
        this.member = member;
        this.travel = travel;
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }
    public Comment(String content, Member member, Travel travel, Comment parent) {
        this.content = content;
        this.member = member;
        this.travel = travel;
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
        this.parent = parent;
    }

    public Comment(Long id, String content, Member member, Travel travel) {
        this.id = id;
        this.content = content;
        this.member = member;
        this.travel = travel;
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }
    public Comment(Long id, String content, Member member, Travel travel, Comment parent) {
        this.id = id;
        this.content = content;
        this.member = member;
        this.travel = travel;
        this.parent = parent;
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }

    // ❌ 사용하지 않는 코드는 제거하면 좋을것 같습니다!
    public void updateComment(String content) {
        this.content = content;
        this.modifiedAt = LocalDateTime.now();
    }

}
