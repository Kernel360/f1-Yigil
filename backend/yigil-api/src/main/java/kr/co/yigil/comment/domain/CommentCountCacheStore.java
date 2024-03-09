package kr.co.yigil.comment.domain;

public interface CommentCountCacheStore {

    int increaseCommentCount(Long travelId);

    int decreaseCommentCount(Long travelId);

}
