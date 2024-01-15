//package kr.co.yigil.comment.domain.repository;
//
//import static kr.co.yigil.comment.domain.QComment.comment;
//
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import java.util.List;
//import kr.co.yigil.comment.domain.Comment;
//import lombok.RequiredArgsConstructor;
//
//@RequiredArgsConstructor
//public class CommentRepositoryImpl implements CommentRepositoryCustom {
//    private final JPAQueryFactory queryFactory;
//
//    @Override
//    public List<Comment> findByTravelId(Long travelId) {
//        return queryFactory.selectFrom(comment)
//                .leftJoin(comment.parent).fetchJoin()
//                .where(comment.travel.id.eq(travelId))
//                .orderBy(comment.parent.id.asc().nullsFirst(),
//                        comment.createdAt.asc())
//                .fetch();
//    }
//
//}
