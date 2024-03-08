package kr.co.yigil.region.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Polygon;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "dong_division")
public class DongDivision {
    @Id
    private int gid;

    @Column
    private String baseDate;

    @Column(name = "adm_nm")
    private String name;

    @Column(name = "adm_cd")
    private String divisionCode;

    @Column(columnDefinition = "geometry(MultiPolygon,5186)", name = "geom")
    private MultiPolygon geometry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

}
