package kr.co.yigil.region.domain;

import java.util.List;
import java.util.stream.Collectors;
import kr.co.yigil.member.domain.MemberReader;
import kr.co.yigil.region.domain.RegionInfo.Category;
import kr.co.yigil.region.domain.RegionInfo.Main;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService{
    private final RegionCategoryReader regionCategoryReader;
    private final MemberReader memberReader;
    @Override
    @Transactional(readOnly = true)
    public List<Category> getAllRegionCategory(Long memberId) {
        var member = memberReader.getMember(memberId);
        var categories = regionCategoryReader.getAllRegionCategory();
        return categories.stream().map(category -> new Category(category, member)).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Main> getMyRegions(Long memberId) {
        var member = memberReader.getMember(memberId);
        return member.getFavoriteRegions().stream()
                .map(MemberRegion::getRegion)
                .map(Main::new)
                .toList();
    }
}
