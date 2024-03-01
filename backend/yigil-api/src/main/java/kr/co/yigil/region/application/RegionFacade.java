package kr.co.yigil.region.application;

import java.util.List;
import kr.co.yigil.region.domain.RegionInfo.Category;
import kr.co.yigil.region.domain.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegionFacade {
    private final RegionService regionService;

    public List<Category> getRegionSelectResponse() {
        return regionService.getAllRegionCategory();
    }
}
