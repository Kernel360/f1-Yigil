package kr.co.yigil.place.interfaces.dto.response;

import java.util.List;
import kr.co.yigil.place.interfaces.dto.PlaceInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegionPlaceResponse {
    private List<PlaceInfoDto> places;
}
