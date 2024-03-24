package kr.co.yigil.favor.domain;

import jakarta.persistence.*;
import kr.co.yigil.member.Member;
import kr.co.yigil.travel.domain.Travel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Favor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "travel_id")
    private Travel travel;

    private final LocalDate createdAt = LocalDate.now();

    public Favor(final Member member, final Travel travel) {
        this.member = member;
        this.travel = travel;
    }
}
