package kr.co.yigil.bookmark.domain;

import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.domain.MemberReader;
import kr.co.yigil.place.Place;
import kr.co.yigil.place.domain.PlaceReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService{
    private final BookmarkStore bookmarkStore;
    private final BookmarkReader bookmarkReader;
    private final MemberReader memberReader;
    private final PlaceReader placeReader;
    @Transactional
    @Override
    public void addBookmark(Long memberId, Long placeId) {
        if (bookmarkReader.isBookmarked(memberId, placeId)) {
            throw new BadRequestException(ExceptionCode.ALREADY_BOOKMARKED);
        }
        Member member = memberReader.getMember(memberId);
        Place place = placeReader.getPlace(placeId);
        bookmarkStore.store(member, place);
    }

    @Transactional
    @Override
    public void deleteBookmark(Long memberId, Long placeId) {
        Member member = memberReader.getMember(memberId);
        Place place = placeReader.getPlace(placeId);
        bookmarkStore.remove(member, place);

    }
}
