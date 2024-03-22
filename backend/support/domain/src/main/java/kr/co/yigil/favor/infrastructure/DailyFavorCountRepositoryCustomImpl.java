package kr.co.yigil.favor.infrastructure;


import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.yigil.favor.domain.DailyFavorCount;
import kr.co.yigil.favor.domain.QDailyFavorCount;
import kr.co.yigil.travel.TravelType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DailyFavorCountRepositoryCustomImpl implements DailyFavorCountRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<DailyFavorCount> findTopByTravelTypeOrderByCountDesc(TravelType travelType){
        QDailyFavorCount dailyFavorCount = QDailyFavorCount.dailyFavorCount;

        BooleanExpression expression;
        if (travelType == TravelType.ALL) {
            expression = dailyFavorCount.travelType.eq(TravelType.SPOT)
                    .or(dailyFavorCount.travelType.eq(TravelType.COURSE));
        } else {
            expression = dailyFavorCount.travelType.eq(travelType);
        }

        return queryFactory.selectFrom(dailyFavorCount)
                .where(expression)
                .orderBy(dailyFavorCount.count.desc())
                .limit(10)
                .fetch();
    }


}
