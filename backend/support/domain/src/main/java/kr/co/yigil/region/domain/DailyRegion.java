package kr.co.yigil.region.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class DailyRegion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;

    @Column
    private Long referenceCount;

    public DailyRegion(LocalDate date, Region region, Long referenceCount) {
        this.date = date;
        this.region = region;
        this.referenceCount = referenceCount;
    }

    public DailyRegion(Region region, Long referenceCount) {
        this.region = region;
        this.referenceCount = referenceCount;
    }
}
