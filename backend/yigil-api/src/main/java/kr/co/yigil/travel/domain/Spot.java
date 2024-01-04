package kr.co.yigil.post.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import kr.co.yigil.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;


@Entity
@Getter
@DiscriminatorValue("SPOT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Spot extends Travel{
    @Column(columnDefinition = "geometry(Point,4326)")
    private Point location;

    private boolean isInCourse;

    @Column(nullable = false, length = 20)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String imageUrl;

    @Column(columnDefinition = "TEXT")
    private String videoUrl;


    public Spot(final Member member, final Point location, final boolean isInCourse, final String title, final String description, final String imageUrl, final String videoUrl) {
        super(member);
        this.location = location;
        this.isInCourse = isInCourse;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.videoUrl = videoUrl;
    }
}
