package kr.co.yigil.bookmark.interfaces;

import kr.co.yigil.auth.Auth;
import kr.co.yigil.auth.MemberOnly;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.bookmark.application.BookmarkFacade;
import kr.co.yigil.bookmark.interfaces.dto.AddBookmarkResponse;
import kr.co.yigil.bookmark.interfaces.dto.DeleteBookmarkResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookmarkController {
    private final BookmarkFacade bookmarkFacade;

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

}
