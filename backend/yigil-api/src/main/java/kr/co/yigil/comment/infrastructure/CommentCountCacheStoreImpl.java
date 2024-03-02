package kr.co.yigil.comment.infrastructure;

import kr.co.yigil.comment.domain.CommentCountCacheReader;
import kr.co.yigil.comment.domain.CommentCountCacheStore;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CommentCountCacheStoreImpl implements CommentCountCacheStore {

    private final CommentCountCacheReader commentCountCacheReader;

    @Override
    @CachePut(value = "commentCount")
    public int increaseCommentCount(Long travelId) {
        int commentCount = commentCountCacheReader.getCommentCount(travelId);
        return ++commentCount;
    }

    @Override
    @CachePut(value = "commentCount")
    public int decreaseCommentCount(Long travelId) {
        int commentCount = commentCountCacheReader.getCommentCount(travelId);
        return --commentCount;
    }
}
