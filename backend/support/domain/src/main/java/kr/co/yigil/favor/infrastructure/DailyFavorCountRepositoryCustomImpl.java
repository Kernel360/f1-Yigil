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
    public List<DailyFavorCount> findTopByTravelTypeOrderByCountDesc(LocalDate startDate, LocalDate endDate){
        QDailyFavorCount dailyFavorCount = QDailyFavorCount.dailyFavorCount;

        BooleanExpression expression;
        expression = dailyFavorCount.travelType.eq(TravelType.SPOT)
                .or(dailyFavorCount.travelType.eq(TravelType.COURSE));


        BooleanExpression dateExpression = dailyFavorCount.createdAt.between(startDate, endDate);


        return queryFactory.selectFrom(dailyFavorCount)
                .where(expression.and(dateExpression))
                .orderBy(dailyFavorCount.count.desc())
                .limit(5)
                .fetch();
    }
}
