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
import org.locationtech.jts.geom.Polygon;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "division2")
public class Division {

    @Id
    private int gid;

    @Column(name = "sigungu_cd")
    private String divisionCode;


    @Column(name = "sigungu_nm")
    private String koreanName;

    @Column(columnDefinition = "geometry(Polygon,5186)", name = "geom")
    private Polygon geometry;

    @Column(name = "city")
    private String city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    public boolean isSeoul() {
        return city.equals("서울시");
    }
}
