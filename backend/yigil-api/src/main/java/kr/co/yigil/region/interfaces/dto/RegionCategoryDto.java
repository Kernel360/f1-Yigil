package kr.co.yigil.region.interfaces.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegionCategoryDto {
    private String categoryName;
    private List<RegionDto> regions;
}
