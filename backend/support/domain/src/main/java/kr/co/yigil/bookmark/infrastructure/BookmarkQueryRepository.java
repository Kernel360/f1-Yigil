package kr.co.yigil.bookmark.infrastructure;

import static org.springframework.util.ObjectUtils.isEmpty;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import kr.co.yigil.bookmark.domain.QBookmark;
import kr.co.yigil.bookmark.domain.dto.BookmarkDto;
import kr.co.yigil.bookmark.domain.dto.QBookmarkDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookmarkQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Slice<BookmarkDto> findAllByMemberId(Long memberId, Pageable pageable) {
        QBookmark bookmark = QBookmark.bookmark;
        BooleanBuilder builder = new BooleanBuilder();

        if (memberId == null) {
            throw new IllegalArgumentException("memberId is null");
        }
        builder.and(bookmark.member.id.eq(memberId));

        Predicate predicate = builder.getValue();
        List<OrderSpecifier<?>> orderSpecifiers = getOrderSpecifiers(pageable.getSort());

        List<BookmarkDto> bookmarks = jpaQueryFactory
            .select(
                new QBookmarkDto(
                    bookmark.id,
                    bookmark.place.id,
                    bookmark.place.name,
                    bookmark.place.imageFile.fileUrl,
                    bookmark.place.rate
                )
            )
            .from(bookmark)
            .where(predicate)
            .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        boolean hasNext = bookmarks.size() > pageable.getPageSize();
        if (hasNext) {
            bookmarks.removeLast();
        }

        return new SliceImpl(bookmarks, pageable, hasNext);
    }

    private List<OrderSpecifier<?>> getOrderSpecifiers(Sort sort) {

        List<OrderSpecifier<?>> specifiers = new ArrayList<>();

        if (!isEmpty(sort)) {
            for (Sort.Order order : sort) {
                if (isSortByProperty(order)) {
                    specifiers.add(
                        getSortedColumn(order.getDirection().isAscending() ? Order.ASC : Order.DESC,
                            QBookmark.bookmark, order.getProperty()));
                }
            }
        }

        return specifiers;
    }

    private static boolean isSortByProperty(Sort.Order order) {
        return order.getProperty().equals("rate") ||
            order.getProperty().equals("createdAt") ||
            order.getProperty().equals("place.name");
    }

    public static OrderSpecifier<?> getSortedColumn(Order order, Path<?> parent, String fieldName) {

        Path<Object> fieldPath = Expressions.path(Object.class, parent, fieldName);
        return new OrderSpecifier(order, fieldPath);

    }
}
