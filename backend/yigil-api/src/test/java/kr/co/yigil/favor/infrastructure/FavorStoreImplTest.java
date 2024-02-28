package kr.co.yigil.favor.infrastructure;

import static org.mockito.Mockito.verify;

import kr.co.yigil.favor.domain.repository.FavorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FavorStoreImplTest {

    @Mock
    private FavorRepository favorRepository;
    @InjectMocks
    private FavorStoreImpl favorStore;

    @DisplayName("save 메서드가 Favor를 잘 저장하는지")
    @Test
    void WhenSaveFavor_ThenShouldNotThrowAnError() {
        favorStore.save(null);

        verify(favorRepository).save(null);
    }

    @DisplayName("deleteFavorById 메서드가 Favor를 잘 삭제하는지")
    @Test
    void WhenDeleteFavorById_ThenShouldNotThrowAnError() {
        favorStore.deleteFavorById(1L);

        verify(favorRepository).deleteById(1L);
    }
}