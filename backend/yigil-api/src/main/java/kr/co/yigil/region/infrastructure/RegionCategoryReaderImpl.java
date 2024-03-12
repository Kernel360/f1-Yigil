package kr.co.yigil.region.infrastructure;

import java.util.List;
import kr.co.yigil.region.domain.RegionCategory;
import kr.co.yigil.region.domain.RegionCategoryReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegionCategoryReaderImpl implements RegionCategoryReader {
    private final RegionCategoryRepository regionCategoryRepository;

    @Override
    public List<RegionCategory> getAllRegionCategory() {
        return regionCategoryRepository.findAll();
    }
}
