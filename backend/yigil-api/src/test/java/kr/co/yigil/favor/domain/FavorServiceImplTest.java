package kr.co.yigil.favor.domain;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import kr.co.yigil.member.Member;
import kr.co.yigil.member.domain.MemberReader;
import kr.co.yigil.travel.domain.Travel;
import kr.co.yigil.travel.domain.TravelReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FavorServiceImplTest {

    @InjectMocks
    private FavorServiceImpl favorService;

    @Mock
    private FavorReader favorReader;
    @Mock
    private MemberReader memberReader;

    @Mock
    private FavorStore favorStore;
    @Mock
    private TravelReader travelReader;
    @Mock
    private FavorCountCacheStore favorCountCacheStore;

    @DisplayName("addFavor 를 호출했을 때 좋아요가 잘 추가되는지 확인")
    @Test
    void WhenAddFavor_ThenShouldReturnOwnersId() {
        Long memberId = 1L;
        Long travelId = 1L;

        Member mockMember = new Member(1L, null, null, null, null, null);
        Travel mockTravel = new Travel(1L, mockMember, null, null, 0, false);

        when(favorReader.existsByMemberIdAndTravelId(memberId, travelId)).thenReturn(false);
        when(memberReader.getMember(memberId)).thenReturn(mockMember);
        when(travelReader.getTravel(travelId)).thenReturn(mockTravel);

        favorService.addFavor(memberId, travelId);

        verify(favorStore).save(any(Favor.class));
        verify(favorCountCacheStore).incrementFavorCount(travelId);
    }

    @DisplayName("deleteFavor 를 호출했을 때 좋아요가 잘 삭제되는지 확인")
    @Test
    void WhenDeleteFavor_ThenShouldNotThrowError() {
        Member member = mock(Member.class);
        when(memberReader.getMember(1L)).thenReturn(member);
        Travel travel = mock(Travel.class);
        when(travelReader.getTravel(1L)).thenReturn(travel);

        when(favorReader.getFavorIdByMemberAndTravel(member, travel)).thenReturn(1L);

        favorService.deleteFavor(1L, 1L);

        verify(favorStore).deleteFavorById(1L);
        verify(favorCountCacheStore).decrementFavorCount(1L);

    }
}