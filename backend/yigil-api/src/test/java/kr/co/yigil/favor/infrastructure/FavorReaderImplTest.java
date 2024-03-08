package kr.co.yigil.favor.infrastructure;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Optional;
import kr.co.yigil.favor.domain.repository.FavorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class FavorReaderImplTest {

    @Mock
    private FavorRepository favorRepository;

    @InjectMocks
    private FavorReaderImpl favorReader;

    @DisplayName("유효한 파라미터를 전달했을 때 existsByMemberIdAndTravelId 메서드가 올바른 결과를 반환하는지")
    @Test
    void GivenValidParameters_WhenCallExistsByMemberIdAndTravelId_ThenShouldReturnBoolean() {
        when(favorRepository.existsByMemberIdAndTravelId(anyLong(), anyLong())).thenReturn(true);

        var result = favorReader.existsByMemberIdAndTravelId(1L, 1L);

        assertThat(result).isTrue();
    }

    @DisplayName("유효하지 않은 파라미터를 전달했을 때 existsByMemberIdAndTravelId 메서드가 올바른 결과를 반환하는지")
    @Test
    void GivenInvalidParameters_WhenCallExistsByMemberIdAndTravelId_ThenShouldReturnBoolean() {
        when(favorRepository.existsByMemberIdAndTravelId(anyLong(), anyLong())).thenReturn(false);

        var result = favorReader.existsByMemberIdAndTravelId(1L, 1L);

        assertThat(result).isFalse();
    }

}