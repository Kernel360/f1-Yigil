package kr.co.yigil.comment.domain;

public interface CommentStore {

    public void save(Comment newComment);

    void delete(Comment comment);
}
