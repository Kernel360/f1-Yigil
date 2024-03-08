package kr.co.yigil.comment.domain;

public interface CommentCountCacheReader {

    int getCommentCount(Long travelId);
}
