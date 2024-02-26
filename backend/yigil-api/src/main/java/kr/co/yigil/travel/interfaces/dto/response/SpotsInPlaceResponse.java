package kr.co.yigil.travel.interfaces.dto.response;

import java.util.List;
import kr.co.yigil.travel.interfaces.dto.SpotInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpotsInPlaceResponse {
    private List<SpotInfoDto> spots;
    private boolean hasNext;
}
