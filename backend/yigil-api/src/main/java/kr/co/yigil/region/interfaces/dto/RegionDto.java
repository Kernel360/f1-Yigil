package kr.co.yigil.region.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegionDto {
    private Long id;
    private String regionName;
    private boolean selected;
}
