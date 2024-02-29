package kr.co.yigil.region.infrastructur;

import java.util.List;
import kr.co.yigil.region.domain.RegionReader;
import kr.co.yigil.region.infrastructure.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegionReaderImpl implements RegionReader {

    private final RegionRepository regionRepository;

    public void validateRegions(List<Long> regionIds) {
        regionIds.forEach(regionId -> {
            if (!regionRepository.existsById(regionId)) {
                throw new IllegalArgumentException("존재하지 않는 지역입니다.");
            }
        });
    }
}
