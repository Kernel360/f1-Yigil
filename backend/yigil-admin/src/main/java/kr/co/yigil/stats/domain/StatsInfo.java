package kr.co.yigil.stats.domain;

import java.util.List;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.domain.Travel;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

public class StatsInfo {
    @Getter
    @ToString
    public static class Recent {
        private final List<RecentTravel> travels;
        private final long travelCnt;

        public Recent(long travelCnt, List<Travel> travels) {
            this.travelCnt = travelCnt;
            this.travels = travels.stream().map(RecentTravel::new).toList();
        }
    }

    @Data
    public static class RecentTravel {
        private final String ownerProfileImageUrl;
        private final String ownerNickname;
        private final String ownerEmail;
        private final String travelName;
        private final String travelUrl;

        public RecentTravel(Travel travel) {
            this.ownerProfileImageUrl = travel.getMember().getProfileImageUrl();
            this.ownerNickname = travel.getMember().getNickname();
            this.ownerEmail = travel.getMember().getEmail();
            if (travel instanceof Spot) {
                this.travelName = ((Spot) travel).getPlace().getName();
                this.travelUrl = "/place/" + ((Spot) travel).getPlace().getId();
            } else if (travel instanceof Course) {
                this.travelName = travel.getTitle();
                this.travelUrl = "/place/" + ((Course) travel).getSpots().getFirst().getPlace().getId();
            } else {
                this.travelName = "";
                this.travelUrl = "";
            }
        }
    }

}
