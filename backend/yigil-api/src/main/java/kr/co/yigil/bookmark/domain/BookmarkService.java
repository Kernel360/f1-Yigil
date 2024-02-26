package kr.co.yigil.bookmark.domain;

public interface BookmarkService {

    void addBookmark(Long memberId, Long placeId);

    void deleteBookmark(Long memberId, Long placeId);
}
