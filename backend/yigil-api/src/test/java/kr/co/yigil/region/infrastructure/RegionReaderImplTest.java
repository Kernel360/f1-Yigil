package kr.co.yigil.region.infrastructure;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.region.domain.Region;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RegionReaderImplTest {

    @Mock
    private RegionRepository regionRepository;

    @InjectMocks
    private RegionReaderImpl regionReader;

    @DisplayName("getRegions 메서드가 id 리스트에 해당하는 Region을 잘 반환하는지")
    @Test
    void getRegions_ReturnsRegions() {
        Long regionId = 1L;
        Region expectedRegion = mock(Region.class);
        when(regionRepository.findById(regionId)).thenReturn(Optional.of(expectedRegion));

        var result = regionReader.getRegions(List.of(1L));
        result.getFirst().equals(expectedRegion);
    }

    @DisplayName("getRegions 메서드가 존재하지 않는 id에 대한 요청에 예외를 잘 발생시키는지")
    @Test
    void getRegions_ThrowsBadRequestException_WhenNotFound() {
        Long regionId = 1L;
        when(regionRepository.findById(regionId)).thenReturn(Optional.empty());
        assertThrows(BadRequestException.class, () -> regionReader.getRegions(List.of(1L)));
    }


}
