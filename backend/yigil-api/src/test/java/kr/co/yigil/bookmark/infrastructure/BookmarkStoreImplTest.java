package kr.co.yigil.bookmark.infrastructure;

import kr.co.yigil.bookmark.domain.Bookmark;
import kr.co.yigil.file.AttachFile;
import kr.co.yigil.file.FileType;
import kr.co.yigil.member.Member;
import kr.co.yigil.place.domain.Place;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class BookmarkStoreImplTest {

    @Mock
    private BookmarkRepository bookmarkRepository;

    @InjectMocks
    private BookmarkStoreImpl bookmarkStore;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("북마크가 요청되었을 때 BookmarkRepository의 save 메서드가 호출되는지")
    @Test
    void whenBookmarkStored_thenSaveIsCalled() {
        Member member = new Member("kiit0901@gmail.com", "123456", "stone", "profile.jpg", "kakao");

        GeometryFactory geometryFactory = new GeometryFactory();
        Point mockPoint = geometryFactory.createPoint(new Coordinate(0, 0));
        AttachFile mockAttachFile = new AttachFile(FileType.IMAGE, "img.url", "original.name", 10L );
        Place mockPlace = new Place("패스트캠퍼스", "봉은사역 근처", 0.0, mockPoint, mockAttachFile, mockAttachFile);

        bookmarkStore.store(member, mockPlace);

        verify(bookmarkRepository, times(1)).save(new Bookmark(member, mockPlace));
    }

    @DisplayName("북마크 삭제가 요청되었을 때 BookmarkRepository의 deleteByMemberAndPlaceId 메서드가 호출되는지")
    @Test
    void whenBookmarkRemoved_thenDeleteByMemberAndPlaceIdIsCalled() {
        Member member = new Member("kiit0901@gmail.com", "123456", "stone", "profile.jpg", "kakao");
        Long placeId = 1L;
        GeometryFactory geometryFactory = new GeometryFactory();
        Point mockPoint = geometryFactory.createPoint(new Coordinate(0, 0));
        AttachFile mockAttachFile = new AttachFile(FileType.IMAGE, "img.url", "original.name", 10L);
        Place mockPlace = new Place("패스트캠퍼스", "봉은사역 근처",0.0, mockPoint, mockAttachFile, mockAttachFile);


        bookmarkStore.remove(member, mockPlace);

        verify(bookmarkRepository, times(1)).deleteByMemberAndPlace(member, mockPlace);
    }
}