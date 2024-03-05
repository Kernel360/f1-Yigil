package kr.co.yigil.member;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import kr.co.yigil.file.AttachFile;
import kr.co.yigil.region.domain.MemberRegion;
import kr.co.yigil.region.domain.Region;
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
    @UniqueConstraint(columnNames = {"social_login_id", "social_login_type"})
})
@SQLDelete(sql = "UPDATE member SET status = 'WITHDRAW' WHERE id = ?")
@Where(clause = "status = 'ACTIVE'")
public class Member {

    private static final String DEFAULT_PROFILE_CDN = "http://cdn.yigil.co.kr/";

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

    @Enumerated(value = EnumType.STRING)
    private Gender gender = Gender.NONE;

    @Enumerated(value = EnumType.STRING)
    private Ages ages = Ages.NONE;

    @Column
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private final List<MemberRegion> favoriteRegions = new ArrayList<>();

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime joinedAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    public Member(final String email, final String socialLoginId, final String nickname,
        final String profileImageUrl, final String socialLoginTypeString) {
        this.email = email;
        this.socialLoginId = socialLoginId;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.status = MemberStatus.ACTIVE;
        this.socialLoginType = SocialLoginType.valueOf(socialLoginTypeString.toUpperCase());
        this.joinedAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }

    public Member(final String email, final String socialLoginId, final String nickname,
        final String profileImageUrl, final SocialLoginType socialLoginType) {
        this.email = email;
        this.socialLoginId = socialLoginId;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.status = MemberStatus.ACTIVE;
        this.socialLoginType = socialLoginType;
        this.joinedAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }

    public Member(final Long id, final String email, final String socialLoginId,
        final String nickname, final String profileImageUrl,
        final SocialLoginType socialLoginType) {
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

    public Member(final Long id, final String email, final String socialLoginId, final String nickname,
        final String profileImageUrl, final SocialLoginType socialLoginType, final Ages ages, final Gender gender) {
        this.id = id;
        this.email = email;
        this.socialLoginId = socialLoginId;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.status = MemberStatus.ACTIVE;
        this.socialLoginType = socialLoginType;
        this.joinedAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
        this.ages = ages;
        this.gender = gender;
    }

    public void updateMemberInfo(final String nickname, final String age, final String gender,
        final AttachFile profileImageFile, final List<MemberRegion> favoriteRegions) {
        this.nickname = nickname;
        this.ages = Ages.from(age);
        this.gender = Gender.from(gender);
        if (profileImageFile != null) {
            this.profileImageUrl = profileImageFile.getFileUrl();
        }
        this.favoriteRegions.clear();
        this.favoriteRegions.addAll(favoriteRegions);
    }

    public String getProfileImageUrl() {
        if (profileImageUrl == null) {
            return null;
        }
        if (profileImageUrl.startsWith("http://") || profileImageUrl.startsWith("https://")) {
            return profileImageUrl;
        } else {
            return DEFAULT_PROFILE_CDN + profileImageUrl;
        }
    }

    public List<Long> getFavoriteRegionIds() {
        return favoriteRegions.stream().map(
            memberRegion -> memberRegion.getRegion().getId()
        ).toList();
    }

    public boolean isFavoriteRegion(Region region) {
        return favoriteRegions.stream()
            .anyMatch(memberRegion -> memberRegion.getRegion().equals(region));
    }
}
