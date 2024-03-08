package kr.co.yigil.place.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.bookmark.domain.BookmarkReader;
import kr.co.yigil.member.Ages;
import kr.co.yigil.member.Gender;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.domain.MemberReader;
import kr.co.yigil.place.domain.PlaceInfo.Main;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
public class PlaceServiceImplTest {

    @Mock
    private PlaceReader placeReader;

    @Mock
    private PopularPlaceReader popularPlaceReader;

    @Mock
    private PlaceCacheReader placeCacheReader;

    @Mock
    private BookmarkReader bookmarkReader;

    @Mock
    private MemberReader memberReader;

    @InjectMocks
    private PlaceServiceImpl placeService;

    @DisplayName("getPopularPlace 메서드가 Info 객체의 List를 잘 반환하는지")
    @Test
    void getPopularPlace_ShouldReturnListOfInfo() {
        Place mockPlace = mock(Place.class);
        when(mockPlace.getId()).thenReturn(1L);
        Accessor mockAccessor = mock(Accessor.class);
        when(mockAccessor.isMember()).thenReturn(true);
        when(mockAccessor.getMemberId()).thenReturn(1L);
        when(placeCacheReader.getSpotCount(1L)).thenReturn(1);
        when(bookmarkReader.isBookmarked(1L, 1L)).thenReturn(true);
        when(popularPlaceReader.getPopularPlace()).thenReturn(List.of(mockPlace));

        List<PlaceInfo.Main> popularPlace = placeService.getPopularPlace(mockAccessor);

        assertNotNull(popularPlace);
    }

    @DisplayName("getPopularPlaceMore 메서드가 Info 객체의 List를 잘 반환하는지")
    @Test
    void getPopularPlaceMore_ShouldReturnListOfInfo() {
        Place mockPlace = mock(Place.class);
        when(mockPlace.getId()).thenReturn(1L);
        Accessor mockAccessor = mock(Accessor.class);
        when(mockAccessor.isMember()).thenReturn(true);
        when(mockAccessor.getMemberId()).thenReturn(1L);
        when(placeCacheReader.getSpotCount(1L)).thenReturn(1);
        when(bookmarkReader.isBookmarked(1L, 1L)).thenReturn(true);
        when(popularPlaceReader.getPopularPlaceMore()).thenReturn(List.of(mockPlace));

        List<PlaceInfo.Main> popularPlaceMore = placeService.getPopularPlaceMore(mockAccessor);

        assertNotNull(popularPlaceMore);
    }

    @DisplayName("getPopularPlaceByDemographics 메서드가 Info 객체의 List를 잘 반환하는지")
    @Test
    void getPopularPlaceByDemographics_ShouldReturnListOfInfo() {
        Member member = mock(Member.class);
        when(memberReader.getMember(anyLong())).thenReturn(member);
        when(member.getAges()).thenReturn(Ages.FIFTIES);
        when(member.getGender()).thenReturn(Gender.MALE);

        Place mockPlace = mock(Place.class);
        List<Place> places = List.of(mockPlace);
        when(placeReader.getPopularPlaceByDemographics(any(), any())).thenReturn(places);
        when(mockPlace.getId()).thenReturn(1L);
        when(placeCacheReader.getSpotCount(anyLong())).thenReturn(1);
        when(bookmarkReader.isBookmarked(anyLong(), anyLong())).thenReturn(true);

        List<PlaceInfo.Main> popularPlaceByDemographics = placeService.getPopularPlaceByDemographics(1L);

        assertNotNull(popularPlaceByDemographics);
    }

    @DisplayName("getPopularPlaceByDemographicsMore 메서드가 Info 객체의 List를 잘 반환하는지")
    @Test
    void getPopularPlaceByDemographicsMore_ShouldReturnListOfInfo() {
        Member member = mock(Member.class);
        when(memberReader.getMember(anyLong())).thenReturn(member);
        when(member.getAges()).thenReturn(Ages.FIFTIES);
        when(member.getGender()).thenReturn(Gender.MALE);

        Place mockPlace = mock(Place.class);
        List<Place> places = List.of(mockPlace);
        when(placeReader.getPopularPlaceByDemographicsMore(any(), any())).thenReturn(places);
        when(mockPlace.getId()).thenReturn(1L);
        when(placeCacheReader.getSpotCount(anyLong())).thenReturn(1);
        when(bookmarkReader.isBookmarked(anyLong(), anyLong())).thenReturn(true);

        List<PlaceInfo.Main> popularPlaceByDemographicsMore = placeService.getPopularPlaceByDemographicsMore(
                1L);

        assertNotNull(popularPlaceByDemographicsMore);
    }

    @DisplayName("getPlaceInRegion 메서드가 Info 객체의 List를 잘 반환하는지")
    @Test
    void getPlaceInRegion_ShouldReturnListOfInfo() {
        Place mockPlace = mock(Place.class);
        when(mockPlace.getId()).thenReturn(1L);
        Accessor mockAccessor = mock(Accessor.class);
        when(mockAccessor.isMember()).thenReturn(true);
        when(mockAccessor.getMemberId()).thenReturn(1L);
        when(placeCacheReader.getSpotCount(1L)).thenReturn(1);
        when(bookmarkReader.isBookmarked(1L, 1L)).thenReturn(true);
        when(placeReader.getPlaceInRegion(1L)).thenReturn(List.of(mockPlace));

        List<PlaceInfo.Main> placeInRegion = placeService.getPlaceInRegion(1L, mockAccessor);

        assertNotNull(placeInRegion);
    }

    @DisplayName("getPlaceInRegionMore 메서드가 Info 객체의 List를 잘 반환하는지")
    @Test
    void getPlaceInRegionMore_ShouldRetrunListOfInfo() {
        Place mockPlace = mock(Place.class);
        when(mockPlace.getId()).thenReturn(1L);
        Accessor mockAccessor = mock(Accessor.class);
        when(mockAccessor.isMember()).thenReturn(true);
        when(mockAccessor.getMemberId()).thenReturn(1L);
        when(placeCacheReader.getSpotCount(1L)).thenReturn(1);
        when(bookmarkReader.isBookmarked(1L, 1L)).thenReturn(true);
        when(placeReader.getPlaceInRegionMore(1L)).thenReturn(List.of(mockPlace));

        List<PlaceInfo.Main> placeInRegionMore = placeService.getPlaceInRegionMore(1L, mockAccessor);

        assertNotNull(placeInRegionMore);
    }

    @DisplayName("retrievePlace 메서드가 Detail 객체를 잘 반환하는지")
    @Test
    void retrievePlace_ShouldReturnDetail() {
        Place mockPlace = mock(Place.class);
        when(mockPlace.getId()).thenReturn(1L);
        Accessor mockAccessor = mock(Accessor.class);
        when(mockAccessor.isMember()).thenReturn(true);
        when(mockAccessor.getMemberId()).thenReturn(1L);
        when(placeCacheReader.getSpotCount(1L)).thenReturn(1);
        when(bookmarkReader.isBookmarked(1L, 1L)).thenReturn(true);
        when(placeReader.getPlace(1L)).thenReturn(mockPlace);

        PlaceInfo.Detail detail = placeService.retrievePlace(1L, mockAccessor);

        assertNotNull(detail);
    }

    @DisplayName("retrievePlace 메서드가 Detail 객체를 잘 반환하는지")
    @Test
    void findPlace_ShouldReturnDetail() {
        Place mockPlace = mock(Place.class);
        when(mockPlace.getId()).thenReturn(1L);
        Accessor mockAccessor = mock(Accessor.class);
        when(mockAccessor.isMember()).thenReturn(true);
        when(mockAccessor.getMemberId()).thenReturn(1L);
        when(placeCacheReader.getSpotCount(1L)).thenReturn(1);
        when(bookmarkReader.isBookmarked(1L, 1L)).thenReturn(true);
        when(placeReader.getPlace(1L)).thenReturn(mockPlace);

        PlaceInfo.Detail detail = placeService.retrievePlace(1L, mockAccessor);

        assertNotNull(detail);
    }

    @DisplayName("findPlaceStaticImage 메서드가 MapStaticImageInfo 객체를 잘 반환하는지")
    @Test
    void findPlaceStaticImage_ShouldReturnMapStaticImageInfo() {
        Place mockPlace = mock(Place.class);
        when(placeReader.findPlaceByNameAndAddress("장소", "장소구 장소면 장소리")).thenReturn(java.util.Optional.of(mockPlace));

        PlaceInfo.MapStaticImageInfo mapStaticImageInfo = placeService.findPlaceStaticImage("장소", "장소구 장소면 장소리");

        assertNotNull(mapStaticImageInfo);
    }

    @DisplayName("getNearPlace 메서드가 Page 객체를 잘 반환하는지")
    @Test
    void getNearPlace_ShouldReturnPage() {
        PlaceCommand.NearPlaceRequest mockCommand = mock(PlaceCommand.NearPlaceRequest.class);
        when(placeReader.getNearPlace(mockCommand)).thenReturn(mock(org.springframework.data.domain.Page.class));

        var result = placeService.getNearPlace(mockCommand);

        assertNotNull(result);
    }

    @DisplayName("getPlaceKeywords 메서드가 Keyword 객체의 List를 잘 반환하는지")
    @Test
    void getPlaceKeywords_ShouldReturnListOfKeyword() {
        when(placeReader.getPlaceKeywords("키워드")).thenReturn(List.of("키워드"));
        var result = placeService.getPlaceKeywords("키워드");

        assertNotNull(result);
    }

    @DisplayName("searchPlace 메서드가 Main 객체의 Slice를 잘 반환하는지")
    @Test
    void searchPlace_ShouldReturnSlice() {
        Place mockPlace = mock(Place.class);
        when(mockPlace.getId()).thenReturn(1L);
        Accessor mockAccessor = mock(Accessor.class);
        when(mockAccessor.isMember()).thenReturn(true);
        when(mockAccessor.getMemberId()).thenReturn(1L);
        when(placeCacheReader.getSpotCount(1L)).thenReturn(1);
        when(bookmarkReader.isBookmarked(1L, 1L)).thenReturn(true);
        Slice<Place> mockSlice = new SliceImpl<>(List.of(mockPlace));
        when(placeReader.getPlacesByKeyword(anyString(), any())).thenReturn(mockSlice);

        var result = placeService.searchPlace("키워드", mock(Pageable.class), mockAccessor);

        assertNotNull(result);
    }

}
