package kr.co.yigil.region.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import kr.co.yigil.region.domain.RegionCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RegionCategoryReaderImplTest {

    @Mock
    private RegionCategoryRepository regionCategoryRepository;

    @InjectMocks
    private RegionCategoryReaderImpl regionCategoryReader;

    @DisplayName("getAllRegionCategory 메서드가 RegionCategory를 잘 반환하는지")
    @Test
    void getAllRegionCategory_ShouldReturnRegionCategory() {
        RegionCategory mockRegionCategory = new RegionCategory();
        when(regionCategoryRepository.findAll()).thenReturn(List.of(mockRegionCategory));
        assertEquals(regionCategoryReader.getAllRegionCategory(), List.of(mockRegionCategory));
    }
}
