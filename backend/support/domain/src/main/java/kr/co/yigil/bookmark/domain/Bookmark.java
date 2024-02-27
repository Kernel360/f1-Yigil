package kr.co.yigil.bookmark.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Objects;
import kr.co.yigil.member.Member;
import kr.co.yigil.place.domain.Place;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;

    public Bookmark(final Member member, final Place place) {
        this.member = member;
        this.place = place;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bookmark bookmark = (Bookmark) o;
        return Objects.equals(member, bookmark.member) &&
                Objects.equals(place, bookmark.place);
    }
    @Override
    public int hashCode() {
        return Objects.hash(member, place);
    }

}
