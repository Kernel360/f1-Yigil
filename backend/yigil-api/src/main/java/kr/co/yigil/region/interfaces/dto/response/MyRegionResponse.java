package kr.co.yigil.region.interfaces.dto.response;

import java.util.List;
import kr.co.yigil.region.interfaces.dto.MyRegionDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MyRegionResponse {
    List<MyRegionDto> regions;
}
