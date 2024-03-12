package kr.co.yigil.place.interfaces.dto.response;

import java.util.List;
import kr.co.yigil.place.interfaces.dto.PlaceCoordinateDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NearPlaceResponse {
    private List<PlaceCoordinateDto> places;
    private int currentPage;
    private int totalPages;
}
