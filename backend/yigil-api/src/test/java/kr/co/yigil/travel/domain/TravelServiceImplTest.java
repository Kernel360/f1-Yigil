package kr.co.yigil.travel.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import kr.co.yigil.global.exception.AuthException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.member.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TravelServiceImplTest {

    @Mock
    private TravelReader travelReader;

    @InjectMocks
    private TravelServiceImpl travelService;

    private Travel travel;
    private Member member;

    @BeforeEach
    void setUp() {
        travel = mock(Travel.class);
        member = mock(Member.class);
        when(travel.getMember()).thenReturn(member);
        when(travelReader.getTravel(1L)).thenReturn(travel);
    }

    @DisplayName("changeOnPublic 메서드가 유효한 memberId가 있을 때 travel의 상태를 public으로 바꾸는지")
    @Test
    void changeOnPublic_WithValidMemberId_ShouldChangeTravelToPublic() {
        doNothing().when(travel).changeOnPublic();
        when(member.getId()).thenReturn(1L);
        travelService.changeOnPublic(1L, 1L);
        verify(travel).changeOnPublic();
    }

    @DisplayName("changeOnPublic 메서드가 유효하지 않은 memberId가 있을 때 예외를 잘 발생시키는지")
    @Test
    void changeOnPublic_WithInvalidMemberId_ShouldThrowAuthException() {
        Exception exception = assertThrows(AuthException.class, () -> {
            travelService.changeOnPublic(1L, 1L);
        });

        assertEquals(ExceptionCode.INVALID_AUTHORITY.getMessage(), exception.getMessage());
    }

    @DisplayName("changeOnPrivate 메서드가 유효한 memberId가 있을 때 travel의 상태를 private으로 바꾸는지")
    @Test
    void changeOnPrivate_WithValidMemberId_ShouldChangeTravelToPrivate() {
        doNothing().when(travel).changeOnPrivate();
        when(member.getId()).thenReturn(1L);
        travelService.changeOnPrivate(1L, 1L);
        verify(travel).changeOnPrivate();
    }

    @DisplayName("changeOnPrivate 메서드가 유효하지 않은 memberId가 있을 때 예외를 잘 발생시키는지")
    @Test
    void changeOnPrivate_WithInvalidMemberId_ShouldThrowAuthException() {
        Exception exception = assertThrows(AuthException.class, () -> {
            travelService.changeOnPrivate(1L, 1L);
        });

        assertEquals(ExceptionCode.INVALID_AUTHORITY.getMessage(), exception.getMessage());
    }
}
