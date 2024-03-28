package kr.co.yigil.travel.domain;

import jakarta.persistence.*;
import kr.co.yigil.file.AttachFile;
import kr.co.yigil.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.LineString;

import java.util.List;

@Entity
@Getter
@DiscriminatorValue("COURSE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Course extends Travel {

    @Column(columnDefinition = "geometry(LineString,4326)")
    private LineString path;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "course_id")
    @OrderColumn(name = "spot_order")
    private List<Spot> spots;

    private int representativeSpotOrder;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "attach_file_id")
    private AttachFile mapStaticImageFile;


    public Course(final Member member, final String title, final String description,
                  final double rate, final LineString path, final boolean isPrivate, final List<Spot> spots,
                  final int representativeSpotOrder, final AttachFile mapStaticImageFile) {
        super(member, title, description, rate, isPrivate);
        this.path = path;
        this.spots = spots;
        this.representativeSpotOrder = representativeSpotOrder;
        this.mapStaticImageFile = mapStaticImageFile;
    }

    public Course(final Long id, final Member member, final String title, final String description,
                  final double rate, final LineString path, final boolean isPrivate, final List<Spot> spots,
                  final int representativeSpotOrder, final AttachFile mapStaticImageFile) {
        super(id, member, title, description, rate, isPrivate);
        this.path = path;
        this.spots = spots;
        this.representativeSpotOrder = representativeSpotOrder;
        this.mapStaticImageFile = mapStaticImageFile;
    }

    public void updateCourse(String title, String description, double rate, LineString lineString, List<Spot> spots, AttachFile mapStaticImageFile) {
        updateTravel(title, description, rate);

        this.path = lineString;
        this.spots.clear();
        this.spots.addAll(spots);
        if (mapStaticImageFile != null) {
            this.mapStaticImageFile = mapStaticImageFile;
        }
    }

    public String getMapStaticImageFileUrl() {
        return mapStaticImageFile.getFileUrl();
    }
}
