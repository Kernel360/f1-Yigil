package kr.co.yigil.travel;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import kr.co.yigil.file.AttachFiles;
import kr.co.yigil.member.Member;
import kr.co.yigil.place.Place;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@DiscriminatorValue("SPOT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Spot extends Travel{
    @Column(columnDefinition = "geometry(Point,4326)")
    private Point location;

    @Setter
    private boolean isInCourse;

    @Column(nullable = false, length = 20)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Embedded
    private AttachFiles attachFiles;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;

    private double rate;

    public Spot(final Long id, Member member, final Point location, final boolean isInCourse, final String title, final String description, final AttachFiles attachFiles, final Place place, final double rate) {
        super(id, member);
        this.location = location;
        this.isInCourse = isInCourse;
        this.title = title;
        this.description = description;
        this.attachFiles = attachFiles;
        this.place = place;
        this.rate = rate;
    }
    public Spot(Member member, final Point location, final boolean isInCourse, final String title, final String description, final AttachFiles attachFiles, final Place place, final double rate) {
        super(member);
        this.location = location;
        this.isInCourse = isInCourse;
        this.title = title;
        this.description = description;
        this.attachFiles = attachFiles;
        this.place = place;
        this.rate = rate;
    }

    public void update(AttachFiles attachFiles, String title, String description, double rate) {
        this.attachFiles = attachFiles;
        this.title = title;
        this.description = description;
        this.rate = rate;
    }

}
