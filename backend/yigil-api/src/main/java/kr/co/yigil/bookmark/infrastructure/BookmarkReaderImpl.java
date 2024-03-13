package kr.co.yigil.bookmark.infrastructure;

import kr.co.yigil.bookmark.domain.BookmarkReader;
import kr.co.yigil.bookmark.domain.dto.BookmarkDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookmarkReaderImpl implements BookmarkReader {
    private final BookmarkRepository bookmarkRepository;
    private final BookmarkQueryRepository bookmarkQueryRepository;

    @Override
    public Slice<BookmarkDto> getBookmarkSlice(Long memberId, Pageable pageable) {
        return bookmarkQueryRepository.findAllByMemberId(memberId, pageable);
    }

    @Override
    public boolean isBookmarked(Long memberId, Long placeId) {
        return bookmarkRepository.existsByMemberIdAndPlaceId(memberId, placeId);
    }
}
