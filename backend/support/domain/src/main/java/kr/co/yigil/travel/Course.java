package kr.co.yigil.travel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderColumn;
import java.util.List;
import kr.co.yigil.file.AttachFile;
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

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "attach_file_id")
    private AttachFile mapStaticImageFile;


    public Course(final Member member , final String title, final String description, final double rate, final LineString path, final boolean isPrivate, final List<Spot> spots, final int representativeSpotOrder, final AttachFile mapStaticImageFile ) {
        super(member, title, description, rate, isPrivate);
        this.path = path;
        this.spots = spots;
        this.representativeSpotOrder = representativeSpotOrder;
        this.mapStaticImageFile = mapStaticImageFile;
    }

    public Course(final Long id, final Member member , final String title, final String description, final double rate, final LineString path, final boolean isPrivate, final List<Spot> spots, final int representativeSpotOrder, final AttachFile mapStaticImageFile) {
        super(id, member, title, description, rate, isPrivate);
        this.path = path;
        this.spots = spots;
        this.representativeSpotOrder = representativeSpotOrder;
        this.mapStaticImageFile = mapStaticImageFile;
    }}
