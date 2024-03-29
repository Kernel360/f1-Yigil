package kr.co.yigil.region.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
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
    @JsonManagedReference
    @JoinColumn(name = "category_id")
    private RegionCategory category;

    @OneToMany(mappedBy = "region", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private final List<MemberRegion> members = new ArrayList<>();

    public String getName(){
        return StringUtils.isEmpty(getName2()) ? getName1() : getName1() + " | " + getName2();
    }
}
