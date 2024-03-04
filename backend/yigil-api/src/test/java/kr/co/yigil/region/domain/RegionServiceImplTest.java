package kr.co.yigil.region.domain;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.domain.MemberReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RegionServiceImplTest {

    @Mock
    private RegionCategoryReader regionCategoryReader;

    @Mock
    private MemberReader memberReader;

    @InjectMocks
    private RegionServiceImpl regionService;

    @DisplayName("getAllRegionCategory 메서드가 Category Info를 잘 반환하는지")
    @Test
    void getAllRegionCategory_ShouldReturnResponse() {
        Member mockMember = mock(Member.class);
        when(memberReader.getMember(anyLong())).thenReturn(mockMember);
        RegionCategory mockCategory = mock(RegionCategory.class);
        when(regionCategoryReader.getAllRegionCategory()).thenReturn(List.of(mockCategory));

        var result = regionService.getAllRegionCategory(1L);
        assertNotNull(result);
    }

    @DisplayName("getMyRegions 메서드가 Main Info를 잘 반환하는지")
    @Test
    void getMyRegions_ShouldReturnResponse() {
        Member mockMember = mock(Member.class);
        when(memberReader.getMember(anyLong())).thenReturn(mockMember);
        var result = regionService.getMyRegions(1L);
        assertNotNull(result);
    }
}
