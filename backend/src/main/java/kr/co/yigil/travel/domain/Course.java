package kr.co.yigil.travel.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderColumn;
import java.util.List;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.locationtech.jts.geom.LineString;

@Entity
@Getter
@DiscriminatorValue("COURSE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Course extends Travel{
    @Column(columnDefinition = "geometry(Point,4326)")
    private LineString path;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "course_id")
    @OrderColumn(name = "spot_order")
    private List<Spot> spots;

    private int representativeSpotOrder;

    @Column(nullable = false, length = 20)
    private String title;

//    @Builder
//    public Course(final Member member, final LineString path, final List<Spot> spots, final int representativeSpotOrder, final String title) {
//        super(member);
//        this.path = path;
//        this.spots = spots;
//        this.representativeSpotOrder = representativeSpotOrder;
//        this.title = title;
//    }

    @Builder
    public Course(final LineString path, final List<Spot> spots, final int representativeSpotOrder, final String title) {
        this.path = path;
        this.spots = spots;
        this.representativeSpotOrder = representativeSpotOrder;
        this.title = title;
    }

    public void deleteSpot(Spot spot){
        if(spots.contains(spot)){
            spots.remove(spot);
        }else {
            throw new BadRequestException(ExceptionCode.NOT_FOUND_SPOT_ID);
        }
    }

    public void addSpot(Spot spot){
        // spots에 이미 spot이 있는지 검증하는 로직이 필요할까요?
        // 의심병 환자처럼 하겠습니다.
        if(spots.contains(spot)){
            return;
        }
        spots.add(spot);
    }
}
