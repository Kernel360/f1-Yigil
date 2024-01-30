package kr.co.yigil.travel.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpotFindListResponse {

    private List<SpotFindDto> spotFindDtos;

    public static SpotFindListResponse from(List<SpotFindDto> spots) {
        return new SpotFindListResponse(spots);
    }


}
