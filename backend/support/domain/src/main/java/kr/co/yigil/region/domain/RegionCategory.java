package kr.co.yigil.region.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class RegionCategory {

    @Id
    private Long id;

    @Column
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Region> regions;
}
