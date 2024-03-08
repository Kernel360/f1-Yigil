package kr.co.yigil.bookmark.domain;

import kr.co.yigil.member.Member;
import kr.co.yigil.place.domain.Place;

public interface BookmarkStore {

    void store(Member member, Place place);

    void remove(Member member, Place place);
}
