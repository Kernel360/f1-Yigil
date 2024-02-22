package kr.co.yigil.member.interfaces.dto.response;

import java.util.List;
import kr.co.yigil.travel.domain.Spot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberSpotResponse {
    private List<Spot> spotList;

    public static MemberSpotResponse from(final List<Spot> spotList) {
        return new MemberSpotResponse(spotList);
    }
}
