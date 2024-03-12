package kr.co.yigil.comment.infrastructure;

import kr.co.yigil.comment.domain.CommentCountCacheReader;
import kr.co.yigil.comment.domain.CommentReader;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentCountCacheReaderImpl implements CommentCountCacheReader {
    private final CommentReader commentReader;
    @Override
    @Cacheable(value = "commentCount")
    public int getCommentCount(Long travelId) {
        return commentReader.getCommentCount(travelId);
    }
}
