package kr.co.yigil.comment.domain;

public interface CommentStore {

    Comment save(Comment newComment);

    void delete(Comment comment);
}
