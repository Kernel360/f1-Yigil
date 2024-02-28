package kr.co.yigil.travel.domain;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class TravelCommand {
    private TravelCommand(){
    }

    @Getter
    @Builder
    @ToString
    public static class VisibilityChangeRequest {
        private List<Long> travelIds;
        private Boolean isPrivate;
    }
}
