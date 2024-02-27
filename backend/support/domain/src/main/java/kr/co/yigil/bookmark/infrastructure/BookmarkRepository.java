package kr.co.yigil.bookmark.infrastructure;

import kr.co.yigil.bookmark.domain.Bookmark;
import kr.co.yigil.member.Member;
import kr.co.yigil.place.domain.Place;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    void deleteByMemberAndPlace(Member member, Place place);

    Slice<Bookmark> findAllByMember(Member member, Pageable pageable);

    boolean existsByMemberIdAndPlaceId(Long memberId, Long placeId);
}
