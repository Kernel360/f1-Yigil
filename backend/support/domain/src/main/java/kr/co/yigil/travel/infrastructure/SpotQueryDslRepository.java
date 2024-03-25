package kr.co.yigil.travel.infrastructure;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.yigil.global.Selected;
import kr.co.yigil.travel.domain.QSpot;
import kr.co.yigil.travel.domain.dto.QSpotListDto;
import kr.co.yigil.travel.domain.dto.SpotListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;

@Repository
@RequiredArgsConstructor
public class SpotQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;
    public Page<SpotListDto> findAllByMemberIdAndIsPrivate(Long memberId, Selected visibility, Pageable pageable) {
        QSpot spot = QSpot.spot;
        BooleanBuilder builder = new BooleanBuilder();

        if(memberId != null) {
            builder.and(spot.member.id.eq(memberId));
        }

        switch (visibility) {
            case Selected.PRIVATE -> builder.and(spot.isPrivate.eq(true));
            case Selected.PUBLIC -> builder.and(spot.isPrivate.eq(false));
            case Selected.ALL -> {
            }// 'all'일 때는 아무런 필터링을 하지 않습니다.
            default ->
                throw new IllegalArgumentException("Invalid visibility value: " + visibility);
        }

        Predicate predicate = builder.getValue();
        List<OrderSpecifier<?>> orderSpecifiers = getOrderSpecifiers(pageable.getSort());

        List<SpotListDto> spots = jpaQueryFactory
            .select(
                new QSpotListDto(
                    spot.id,
                    spot.place.id,
                    spot.place.name,
                    spot.rate,
                    spot.place.imageFile.fileUrl,
                    spot.createdAt,
                    spot.isPrivate
                )
            )
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
                if(order.getProperty().equals("rate")  || order.getProperty().equals("createdAt"))
                    specifiers.add(getSortedColumn(order.getDirection().isAscending() ? Order.ASC : Order.DESC, QSpot.spot, order.getProperty()));
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