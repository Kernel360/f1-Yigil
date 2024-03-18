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
import java.time.LocalDateTime;
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

    public Place(final String name, final String address,
        final Point location, final AttachFile imageFile, final AttachFile mapStaticImageFile, final LocalDateTime latestUploadedTime) {
        this.name = name;
        this.address = address;
        this.location = location;
        this.imageFile = imageFile;
        this.mapStaticImageFile = mapStaticImageFile;
        this.latestUploadedTime = latestUploadedTime;
    }

    public Place(Long id, final String name, final String address,
        final Point location, final AttachFile imageFile, final AttachFile mapStaticImageFile, final LocalDateTime latestUploadedTime) {
        this.id = id;
        this.name = name;
        this.address = address;
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

}
