package kr.co.yigil.favor.infrastructure;


import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.yigil.favor.domain.DailyFavorCount;
import kr.co.yigil.favor.domain.QDailyFavorCount;
import kr.co.yigil.travel.TravelType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DailyFavorCountRepositoryCustomImpl implements DailyFavorCountRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<DailyFavorCount> findTopByTravelTypeOrderByCountDesc(LocalDate startDate, LocalDate endDate, TravelType travelType, Integer limit){
        QDailyFavorCount dailyFavorCount = QDailyFavorCount.dailyFavorCount;

        BooleanExpression expression;
        if (travelType == TravelType.ALL) {
            expression = dailyFavorCount.travelType.eq(TravelType.SPOT)
                    .or(dailyFavorCount.travelType.eq(TravelType.COURSE));
        } else {
            expression = dailyFavorCount.travelType.eq(travelType);
        }

        BooleanExpression dateExpression = dailyFavorCount.createdAt.between(startDate, endDate);

        if(limit == null){
            limit = 10;
        }

        return queryFactory.selectFrom(dailyFavorCount)
                .where(expression.and(dateExpression))
                .orderBy(dailyFavorCount.count.desc())
                .limit(limit)
                .fetch();
    }
}
