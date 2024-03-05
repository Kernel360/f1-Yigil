package kr.co.yigil.region.interfaces.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;
import kr.co.yigil.region.domain.RegionInfo;
import kr.co.yigil.region.interfaces.dto.MyRegionDto;
import kr.co.yigil.region.interfaces.dto.RegionCategoryDto;
import kr.co.yigil.region.interfaces.dto.RegionDto;
import kr.co.yigil.region.interfaces.dto.response.MyRegionResponse;
import kr.co.yigil.region.interfaces.dto.response.RegionSelectResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegionMapper {

    default RegionSelectResponse toRegionSelectResponse(List<RegionInfo.Category> categories) {
        List<RegionCategoryDto> categoryDtos = categories.stream()
                .map(this::toRegionCategoryDto)
                .collect(Collectors.toList());
        return new RegionSelectResponse(categoryDtos);
    }

    default RegionCategoryDto toRegionCategoryDto(RegionInfo.Category category) {
        if (category == null) {
            return null;
        }
        List<RegionDto> regions = category.getItems().stream()
                .map(item -> new RegionDto(item.getId(), item.getName(), item.isSelected()))
                .collect(Collectors.toList());

        return new RegionCategoryDto(category.getName(), regions);
    }

    default MyRegionResponse toMyRegionResponse (List<RegionInfo.Main> mains) {
        if (mains == null) return null;

        List<MyRegionDto> regions = mains.stream()
                .map(main -> new MyRegionDto(main.getId(), main.getName()))
                .collect(Collectors.toList());

        return new MyRegionResponse(regions);
    }
}