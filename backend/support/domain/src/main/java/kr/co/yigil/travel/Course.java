package kr.co.yigil.travel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderColumn;
import java.util.List;
import kr.co.yigil.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.LineString;

@Entity
@Getter
@DiscriminatorValue("COURSE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Course extends Travel{
    @Column(columnDefinition = "geometry(LineString,4326)")
    private LineString path;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id")
    @OrderColumn(name = "spot_order")
    private List<Spot> spots;

    private int representativeSpotOrder;

    @Column(nullable = false, length = 20)
    private String title;

    public Course(final Member member, final LineString path, final List<Spot> spots, final int representativeSpotOrder, final String title) {
        super(member);
        this.path = path;
        this.spots = spots;
        this.representativeSpotOrder = representativeSpotOrder;
        this.title = title;
    }

    public Course(final Member member, final Long id, final LineString path, final List<Spot> spots, final int representativeSpotOrder, final String title) {
        super(id, member);
        this.path = path;
        this.spots = spots;
        this.representativeSpotOrder = representativeSpotOrder;
        this.title = title;
    }
}
