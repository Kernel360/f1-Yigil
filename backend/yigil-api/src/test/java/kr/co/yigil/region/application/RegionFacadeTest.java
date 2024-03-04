package kr.co.yigil.region.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import kr.co.yigil.region.domain.RegionInfo.Category;
import kr.co.yigil.region.domain.RegionInfo.Main;
import kr.co.yigil.region.domain.RegionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RegionFacadeTest {

    @Mock
    private RegionService regionService;

    @InjectMocks
    private RegionFacade regionFacade;

    @DisplayName("getRegionSelectList 메서드가 Category Info를 잘 반환하는지")
    @Test
    void getRegionSelectList_ShouldReturnResponse() {
        Category mockCategory = mock(Category.class);

        when(regionService.getAllRegionCategory(1L)).thenReturn(List.of(mockCategory));

        var result = regionFacade.getRegionSelectList(1L);

        assertEquals(result, List.of(mockCategory));
        verify(regionService).getAllRegionCategory(1L);
    }

    @DisplayName("getMyRegions 메서드가 응답을 잘 반환하는지")
    @Test
    void getMyRegions_ShouldReturnResponse() {
        Main mockMain = mock(Main.class);

        when(regionService.getMyRegions(1L)).thenReturn(List.of(mockMain));

        var result = regionFacade.getMyRegions(1L);

        assertEquals(result, List.of(mockMain));
        verify(regionService).getMyRegions(1L);
    }
}
