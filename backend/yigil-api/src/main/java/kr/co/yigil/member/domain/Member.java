package kr.co.yigil.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"socialLoginId", "socialLoginType"})
})
@SQLDelete(sql = "UPDATE member SET status = 'WITHDRAW' WHERE id = ?")
@Where(clause = "status = 'ACTIVE'")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(nullable = false, length = 30)
    private String socialLoginId;

    @Column(nullable = false, length = 20)
    private String nickname;

    @Column(columnDefinition = "TEXT")
    private String profileImageUrl;

    @Enumerated(value = EnumType.STRING)
    private MemberStatus status;

    @Enumerated(value = EnumType.STRING)
    private SocialLoginType socialLoginType;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime joinedAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    public Member(final String email, final String socialLoginId, final String nickname, final String profileImageUrl, final String socialLoginTypeString) {
        this.email = email;
        this.socialLoginId = socialLoginId;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.status = MemberStatus.ACTIVE;
        this.socialLoginType = SocialLoginType.valueOf(socialLoginTypeString.toUpperCase());
        this.joinedAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }

    public Member(final Long id, final String email, final String socialLoginId, final String nickname, final String profileImageUrl, final SocialLoginType socialLoginType) {
        this.id = id;
        this.email = email;
        this.socialLoginId = socialLoginId;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.status = MemberStatus.ACTIVE;
        this.socialLoginType = socialLoginType;
        this.joinedAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }

}
