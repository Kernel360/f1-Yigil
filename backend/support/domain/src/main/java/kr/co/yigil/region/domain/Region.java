package kr.co.yigil.region.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Region {
    @Id
    private Long id;

    @Column
    private String name1;

    @Column
    private String name2;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private RegionCategory category;
}
