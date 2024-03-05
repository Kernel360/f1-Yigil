package kr.co.yigil.travel.infrastructure;

import static org.springframework.util.ObjectUtils.isEmpty;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import kr.co.yigil.travel.domain.QSpot;
import kr.co.yigil.travel.domain.Spot;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SpotQueryDslRepository {

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

        List<OrderSpecifier<?>> orderSpecifiers = getOrderSpecifiers(pageable.getSort());

        List<Spot> spots = jpaQueryFactory.select(spot)
            .from(spot)
            .where(predicate)
            .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        long total = jpaQueryFactory.selectFrom(spot)
            .where(predicate)
            .fetchCount();

        return new PageImpl<>(spots, pageable, total);

    }

    private List<OrderSpecifier<?>> getOrderSpecifiers(Sort sort){

        List<OrderSpecifier<?>> specifiers = new ArrayList<>();

        if(!isEmpty(sort)){
            for(Sort.Order order:sort){
                switch (order.getProperty()){
                    case "createdAt":
                        specifiers.add(getSortedColumn(order.getDirection().isAscending() ? Order.ASC : Order.DESC, QSpot.spot, "createdAt"));
                        break;
                    case "rate":
                        specifiers.add(getSortedColumn(order.getDirection().isAscending() ? Order.ASC : Order.DESC, QSpot.spot, "rate"));
                        break;
                    default:
                        specifiers.add(getSortedColumn(order.getDirection().isAscending() ? Order.ASC : Order.DESC, QSpot.spot, "createdAt"));

                }
                OrderSpecifier<?> specifier = getSortedColumn(order.getDirection().isAscending() ? Order.ASC : Order.DESC, QSpot.spot, order.getProperty());
                specifiers.add(specifier);
            }
        }
        return specifiers;
    }

    public static OrderSpecifier<?> getSortedColumn(Order order, Path<?> parent, String fieldName) {
        Path<Object> fieldPath = Expressions.path(Object.class, parent, fieldName);

        return new OrderSpecifier(order, fieldPath);
    }

}