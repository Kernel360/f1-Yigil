package kr.co.yigil.region.infrastructur;

import java.util.List;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.region.domain.Region;
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

    @Override
    public List<Region> getRegions(List<Long> favoriteRegionIds) {
        return favoriteRegionIds.stream().map(regionRepository::findById).map(
            region -> region.orElseThrow(
                () -> new BadRequestException(ExceptionCode.NOT_FOUND_REGION_ID)
            )
        ).toList();
    }
}
