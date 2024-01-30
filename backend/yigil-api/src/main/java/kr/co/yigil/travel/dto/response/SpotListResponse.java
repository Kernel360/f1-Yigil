package kr.co.yigil.travel.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpotListResponse {

    private List<SpotFindDto> spotFindDtos;

    public static SpotListResponse from(List<SpotFindDto> spots) {
        return new SpotListResponse(spots);
    }


}
