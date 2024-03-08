package kr.co.yigil.bookmark.infrastructure;

import kr.co.yigil.bookmark.domain.Bookmark;
import kr.co.yigil.bookmark.domain.BookmarkReader;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.domain.MemberReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookmarkReaderImpl implements BookmarkReader {
    private final MemberReader memberReader;
    private final BookmarkRepository bookmarkRepository;

    @Override
    public Slice<Bookmark> getBookmarkSlice(Long memberId, Pageable pageable) {
        Member member = memberReader.getMember(memberId);
        return bookmarkRepository.findAllByMember(member, pageable);
    }

    @Override
    public boolean isBookmarked(Long memberId, Long placeId) {
        return bookmarkRepository.existsByMemberIdAndPlaceId(memberId, placeId);
    }
}
