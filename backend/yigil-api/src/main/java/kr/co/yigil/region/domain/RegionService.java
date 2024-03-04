package kr.co.yigil.region.domain;

import java.util.List;
import kr.co.yigil.region.domain.RegionInfo.Category;
import kr.co.yigil.region.domain.RegionInfo.Main;

public interface RegionService {
    public List<Category> getAllRegionCategory(Long memberId);
    public List<Main> getMyRegions(Long memberId);
}
