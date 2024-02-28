package kr.co.yigil.bookmark.infrastructure;

import kr.co.yigil.bookmark.domain.Bookmark;
import kr.co.yigil.bookmark.domain.BookmarkStore;
import kr.co.yigil.member.Member;
import kr.co.yigil.place.domain.Place;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookmarkStoreImpl implements BookmarkStore {
    private final BookmarkRepository bookmarkRepository;
    @Override
    public void store(Member member, Place place) {
        bookmarkRepository.save(new Bookmark(member, place));
    }

    @Override
    public void remove(Member member, Place place) {
        bookmarkRepository.deleteByMemberAndPlace(member, place);
    }

}
