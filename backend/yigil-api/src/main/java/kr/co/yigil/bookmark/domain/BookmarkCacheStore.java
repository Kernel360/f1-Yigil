package kr.co.yigil.bookmark.domain;

public interface BookmarkCacheStore {
    boolean isBookmarkExist(Long memberId, Long placeId);
}
