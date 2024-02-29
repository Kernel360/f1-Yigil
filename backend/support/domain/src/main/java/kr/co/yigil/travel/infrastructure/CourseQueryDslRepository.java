package kr.co.yigil.travel.infrastructure;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import kr.co.yigil.travel.domain.QCourse;
import kr.co.yigil.travel.domain.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CourseQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Page<Course> findAllByMemberIdAndIsPrivate(Long memberId, String visibility, Pageable pageable) {
        QCourse course = QCourse.course;
        BooleanBuilder builder = new BooleanBuilder();

        if(memberId != null) {
            builder.and(course.member.id.eq(memberId));
        }

        if(!"all".equals(visibility)) {
            builder.and(course.isPrivate.eq("private".equals(visibility)));
        }

        Predicate predicate = builder.getValue();

        List<Course> courses = jpaQueryFactory.select(course)
            .from(course)
            .where(predicate)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        long total = jpaQueryFactory.selectFrom(course)
            .where(predicate)
            .fetchCount();

        return new PageImpl<>(courses, pageable, total);
    }

}
