package kr.co.yigil.bookmark.interfaces.controller;

import kr.co.yigil.auth.Auth;
import kr.co.yigil.auth.MemberOnly;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.bookmark.application.BookmarkFacade;
import kr.co.yigil.bookmark.domain.Bookmark;
import kr.co.yigil.bookmark.interfaces.dto.mapper.BookmarkMapper;
import kr.co.yigil.bookmark.interfaces.dto.response.AddBookmarkResponse;
import kr.co.yigil.bookmark.interfaces.dto.response.BookmarksResponse;
import kr.co.yigil.bookmark.interfaces.dto.response.DeleteBookmarkResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookmarkApiController {
    private final BookmarkFacade bookmarkFacade;
    private final BookmarkMapper bookmarkMapper;

    @PostMapping("/api/v1/add-bookmark/{place_id}")
    @MemberOnly
    public ResponseEntity<AddBookmarkResponse> addBookmark(
            @Auth final Accessor accessor,
            @PathVariable("place_id") final Long placeId
    ) {
        bookmarkFacade.addBookmark(accessor.getMemberId(), placeId);
        return ResponseEntity.ok(new AddBookmarkResponse("장소 북마크 추가 성공"));
    }

    @PostMapping("/api/v1/delete-bookmark/{place_id}")
    @MemberOnly
    public ResponseEntity<DeleteBookmarkResponse> deleteBookmark(
            @Auth final Accessor accessor,
            @PathVariable("place_id") final Long placeId
    ) {
        bookmarkFacade.deleteBookmark(accessor.getMemberId(), placeId);
        return ResponseEntity.ok(new DeleteBookmarkResponse("장소 북마크 제거 성공"));
    }

    @GetMapping("/api/v1/bookmarks")
    @MemberOnly
    public ResponseEntity<BookmarksResponse> getBookmarks(
            @Auth final Accessor accessor,
            @PageableDefault(size = 5) Pageable pageable,
            @RequestParam(name = "sortBy", defaultValue = "createdAt", required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = "desc", required = false) String sortOrder
    ) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        Slice<Bookmark> bookmarkSlice = bookmarkFacade.getBookmarkSlice(accessor.getMemberId(), pageRequest);
        BookmarksResponse response = bookmarkMapper.bookmarkSliceToBookmarksResponse(bookmarkSlice);
        return ResponseEntity.ok(response);
    }


}
