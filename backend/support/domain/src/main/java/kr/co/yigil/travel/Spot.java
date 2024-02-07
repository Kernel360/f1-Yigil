package kr.co.yigil.travel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"place_id", "member_id"}))
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

    public Spot(final Long id, Member member, final Point location, final boolean isInCourse,
        final String title, final String description, final AttachFiles attachFiles,
        final Place place, final double rate) {
        super(id, member, title, description, rate, false);
        this.location = location;
        this.isInCourse = isInCourse;
        this.attachFiles = attachFiles;
        this.place = place;
    }

    public Spot(Member member, final Point location, final boolean isInCourse, final String title,
        final String description, final AttachFiles attachFiles, final Place place,
        final double rate) {
        super(member, title, description, rate, false);
        this.location = location;
        this.isInCourse = isInCourse;
        this.attachFiles = attachFiles;
        this.place = place;
    }


}
