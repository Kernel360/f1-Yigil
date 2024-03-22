package kr.co.yigil.stats.interfaces.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegionStatsResponse {
    private List<RegionStatsInfo> regionStatsInfoList;

    @Data
    public static class RegionStatsInfo {
        private String region;
        private Long referenceCount;
    }

}
