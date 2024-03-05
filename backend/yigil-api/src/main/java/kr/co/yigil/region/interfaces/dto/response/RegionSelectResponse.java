package kr.co.yigil.region.interfaces.dto.response;

import java.util.List;
import kr.co.yigil.region.interfaces.dto.RegionCategoryDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegionSelectResponse {
    private List<RegionCategoryDto> categories;
}
