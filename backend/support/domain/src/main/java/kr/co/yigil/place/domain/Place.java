package kr.co.yigil.place.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import kr.co.yigil.file.AttachFile;
import kr.co.yigil.region.domain.Region;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"name", "address"})})
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    @Column(columnDefinition = "double default 0")
    private double rate;

    @Column(columnDefinition = "geometry(Point,5186)")
    private Point location;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "map_static_image_file_id")
    private AttachFile mapStaticImageFile;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "image_file_id")
    private AttachFile imageFile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    public Place(final String name, final String address, final double rate,
        final Point location, final AttachFile imageFile, final AttachFile mapStaticImageFile) {
        this.name = name;
        this.address = address;
        this.rate = rate;
        this.location = location;
        this.imageFile = imageFile;
        this.mapStaticImageFile = mapStaticImageFile;
    }

    public Place(Long id, final String name, final String address, final double rate,
        final Point location, final AttachFile imageFile, final AttachFile mapStaticImageFile) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.rate = rate;
        this.location = location;
        this.imageFile = imageFile;
        this.mapStaticImageFile = mapStaticImageFile;
    }

    public void updateRegion(Region region) {
        this.region = region;
    }
}
