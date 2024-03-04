package kr.co.yigil.region.application;

import java.util.List;
import kr.co.yigil.region.domain.Region;
import kr.co.yigil.region.domain.RegionInfo.Category;
import kr.co.yigil.region.domain.RegionInfo.Main;
import kr.co.yigil.region.domain.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegionFacade {
    private final RegionService regionService;

    public List<Category> getRegionSelectList(Long memberId) {
        return regionService.getAllRegionCategory(memberId);
    }

    public List<Main> getMyRegions(Long memberId) {
        return regionService.getMyRegions(memberId);
    }
}
