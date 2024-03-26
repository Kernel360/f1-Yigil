package kr.co.yigil.follow.domain;

import jakarta.persistence.*;
import kr.co.yigil.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private Member follower;

    @ManyToOne
    @JoinColumn(name = "following_id")
    private Member following;

    @CreatedDate
    private LocalDateTime createdAt;

    public Follow(final Member follower, final Member following) {
        this.follower = follower;
        this.following = following;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Follow follow = (Follow) o;
        return Objects.equals(follower, follow.follower) &&
                Objects.equals(following, follow.following);
    }

    @Override
    public int hashCode() {
        return Objects.hash(follower, following);
    }
}
