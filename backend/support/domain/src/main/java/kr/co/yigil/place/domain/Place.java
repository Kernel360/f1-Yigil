package kr.co.yigil.place.domain;

import jakarta.persistence.*;
import kr.co.yigil.file.AttachFile;
import kr.co.yigil.region.domain.Region;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"name", "address"})}
)
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(255)")
    private String name;

    private String address;

    private LocalDateTime latestUploadedTime;

    @Column(columnDefinition = "double precision default 0")
    private double rate;

    @Column(columnDefinition = "geometry(Point,4326)")
    private Point location;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "map_static_image_file_id")
    private AttachFile mapStaticImageFile;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "image_file_id")
    private AttachFile imageFile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    public Place(final String name, final String address, final double rate,
        final Point location, final AttachFile imageFile, final AttachFile mapStaticImageFile, final LocalDateTime latestUploadedTime) {
        this.name = name;
        this.address = address;
        this.rate = rate;
        this.location = location;
        this.imageFile = imageFile;
        this.mapStaticImageFile = mapStaticImageFile;
        this.latestUploadedTime = latestUploadedTime;
    }

    public Place(Long id, final String name, final String address, final double rate,
        final Point location, final AttachFile imageFile, final AttachFile mapStaticImageFile, final LocalDateTime latestUploadedTime) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.rate = rate;
        this.location = location;
        this.imageFile = imageFile;
        this.mapStaticImageFile = mapStaticImageFile;
        this.latestUploadedTime = latestUploadedTime;
    }

    public String getImageFileUrl() {
        return imageFile.getFileUrl();
    }

    public String getMapStaticImageFileUrl() {
        return mapStaticImageFile.getFileUrl();
    }

    public void updateRegion(Region region) {
        this.region = region;
    }

    public void updateLatestUploadedTime() {
        this.latestUploadedTime = LocalDateTime.now();
    }

    public void updateImage(AttachFile updatedImage) {
        this.imageFile = updatedImage;
    }
}
