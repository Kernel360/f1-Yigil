package kr.co.yigil.region.domain;

import java.util.List;
import kr.co.yigil.region.domain.RegionInfo.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService{
    private final RegionCategoryReader regionReader;

    @Override
    @Transactional(readOnly = true)
    public List<Category> getAllRegionCategory() {
        var categories = regionReader.getAllRegionCategory();
        return categories.stream().map(Category::new).toList();
    }
}
