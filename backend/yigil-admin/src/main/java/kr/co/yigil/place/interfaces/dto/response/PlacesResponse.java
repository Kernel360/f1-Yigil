package kr.co.yigil.place.interfaces.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlacesResponse {
    private List<PlaceInfoDto> places;

    @Data
    public static class PlaceInfoDto {
        private Long id;
        private String name;
        private PointDto location;
    }


}
