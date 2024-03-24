package kr.co.yigil.travel.domain;

import jakarta.persistence.*;
import kr.co.yigil.file.AttachFile;
import kr.co.yigil.file.AttachFiles;
import kr.co.yigil.member.Member;
import kr.co.yigil.place.domain.Place;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.locationtech.jts.geom.Point;

import java.util.List;

@Entity
@Getter
@DiscriminatorValue("SPOT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Spot extends Travel {

    @Column(columnDefinition = "geometry(Point,4326)")
    private Point location;

    @Setter
    private boolean isInCourse;

    @Embedded
    private AttachFiles attachFiles;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "place_id")
    private Place place;

    public Spot(final Long id, final Member member, final Point location, final boolean isInCourse, final String description, final AttachFiles attachFiles,
        final Place place, final double rate) {
        super(id, member, "", description, rate, false);
        this.location = location;
        this.isInCourse = isInCourse;
        this.attachFiles = attachFiles;
        this.place = place;
        this.place.updateLatestUploadedTime();
    }


    public Spot(final Member member, final Point location, final boolean isInCourse,
            final String description, final AttachFiles attachFiles, final Place place,
            final double rate) {
        super(member, "", description, rate, false);
        this.location = location;
        this.isInCourse = isInCourse;
        this.attachFiles = attachFiles;
        this.place = place;
        this.place.updateLatestUploadedTime();
    }

    public void updateSpot(double rate, final String description, final List<AttachFile> attachFiles) {
        updateTravel(description, rate);
        this.attachFiles.updateFiles(attachFiles);
    }

    public void changeInCourse() {
        this.isInCourse = true;
    }
    public void changeOutOfCourse() { this.isInCourse = false; }

    public List<String> getImageUrls(){
        return attachFiles.getUrls();
    }
}
