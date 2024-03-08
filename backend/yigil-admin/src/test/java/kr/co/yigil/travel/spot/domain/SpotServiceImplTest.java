package kr.co.yigil.travel.spot.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import kr.co.yigil.comment.domain.CommentReader;
import kr.co.yigil.favor.domain.FavorReader;
import kr.co.yigil.file.AttachFile;
import kr.co.yigil.file.AttachFiles;
import kr.co.yigil.member.Member;
import kr.co.yigil.place.domain.Place;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.spot.domain.SpotInfoDto.SpotPageInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class SpotServiceImplTest {

    @Mock
    private SpotReader spotReader;
    @Mock
    private SpotStore spotStore;
    @Mock
    private FavorReader favorReader;
    @Mock
    private CommentReader commentReader;
    @InjectMocks
    private SpotServiceImpl spotServiceImpl;

    @DisplayName("getSpots 메서드가 SpotReader를 잘 호출하고 SpotPageInfo를 반환하는지")
    @Test
    void whenGetSpots_thenShouldReturnSpotPageInfo() {

        Pageable pageable = PageRequest.of(0, 5);
        Spot spot = mock(Spot.class);
        List<Spot> spots = List.of(spot);
        Page<Spot> pageSpots = new PageImpl<>(spots, pageable, spots.size());
        when(spotReader.getSpots(any(Pageable.class))).thenReturn(pageSpots);

        SpotInfoDto.SpotAdditionalInfo additionalInfo = new SpotInfoDto.SpotAdditionalInfo(1, 1);
        when(favorReader.getFavorCount(any(Long.class))).thenReturn(additionalInfo.getFavorCount());
        when(commentReader.getCommentCount(any(Long.class))).thenReturn(
            additionalInfo.getCommentCount());

        SpotPageInfo result = spotServiceImpl.getSpots(pageable);

        // Assert
        assertEquals(spots.size(), result.getSpots().getContent().size());
        assertEquals(pageSpots.getTotalElements(), result.getSpots().getTotalElements());
        assertEquals(pageSpots.getPageable(), result.getSpots().getPageable());
    }

    @DisplayName("getSpot 메서드가 SpotReader를 잘 호출하고 SpotDetailInfo를 반환하는지")
    @Test
    void whenGetSpot_thenShouldReturnSpotDetailInfo() {
        Long spotId = 1L;
        AttachFile mockAttachFile = new AttachFile(null, "url", "filename", 4L);
        Place mockPlace = new Place(1L, "name", "address", 4.0, null, mockAttachFile,
            mockAttachFile, null);
        AttachFiles attachFiles = new AttachFiles(List.of(mockAttachFile, mockAttachFile));
        Spot spot = new Spot(1L, mock(Member.class), null, false, null, null, attachFiles,
            mockPlace, 5.0);
        when(spotReader.getSpot(spotId)).thenReturn(spot);
        SpotInfoDto.SpotAdditionalInfo additionalInfo = new SpotInfoDto.SpotAdditionalInfo(1, 1);
        when(favorReader.getFavorCount(any(Long.class))).thenReturn(additionalInfo.getFavorCount());
        when(commentReader.getCommentCount(any(Long.class))).thenReturn(
            additionalInfo.getCommentCount());

        var result = spotServiceImpl.getSpot(spotId);

        assertThat(result).isInstanceOf(SpotInfoDto.SpotDetailInfo.class);
    }

    @DisplayName("deleteSpot 메서드가 SpotStore를 잘 호출하고 memberId를 반환하는지")
    @Test
    void whenGeleteSpot_thenShouldReturnMemberId() {
        Spot mockSpot = mock(Spot.class);
        Member mockMember = mock(Member.class);
        when(spotReader.getSpot(anyLong())).thenReturn(mockSpot);
        when(mockSpot.getMember()).thenReturn(mockMember);
        when(mockMember.getId()).thenReturn(1L);

        var result = spotServiceImpl.deleteSpot(1L);

        assertEquals(1L, result);
        verify(spotStore).deleteSpot(mockSpot);
    }
}