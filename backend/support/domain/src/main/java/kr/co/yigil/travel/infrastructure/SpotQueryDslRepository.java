package kr.co.yigil.travel.infrastructure;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import kr.co.yigil.travel.domain.QSpot;
import kr.co.yigil.travel.domain.Spot;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SpotQueryDslRepository {

    @PersistenceContext
    private final JPAQueryFactory jpaQueryFactory;
    public Page<Spot> findAllByMemberIdAndIsPrivate(Long memberId, String visibility, Pageable pageable) {
        QSpot spot = QSpot.spot;
        BooleanBuilder builder = new BooleanBuilder();

        if(memberId != null) {
            builder.and(spot.member.id.eq(memberId));
        }

        builder.and(spot.isInCourse.eq(false));

        if(!"all".equals(visibility)) {
            builder.and(spot.isPrivate.eq("private".equals(visibility)));
        }

        Predicate predicate = builder.getValue();

        List<Spot> spots = jpaQueryFactory.selectFrom(spot)
            .where(predicate)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        long total = jpaQueryFactory.selectFrom(spot)
            .where(predicate)
            .fetchCount();

        return new PageImpl<>(spots, pageable, total);

    }

}