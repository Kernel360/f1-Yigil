package kr.co.yigil.stats.interfaces.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class  RecentRegionStatsResponse {
    private List<RecentTravel> travels;
    private long travelCnt;

    @Data
    public static class RecentTravel {
        private String ownerProfileImageUrl;
        private String ownerNickname;
        private String ownerEmail;
        private String travelName;
        private String travelUrl;
    }
}

