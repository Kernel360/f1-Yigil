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
public class DailyFavorCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long count;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_id")
    private Travel travel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private Member writer;

    private LocalDate createdAt;

    public DailyFavorCount(Long count, Travel travel, LocalDate createdAt) {
        this.count = count;
        this.travel = travel;
        this.writer = travel.getMember();
        this.createdAt = createdAt;
    }
}
