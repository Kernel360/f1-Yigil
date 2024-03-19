package kr.co.yigil.place.interfaces.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlacesResponse {
    private Page<PlaceInfoDto> places;

    @Data
    public static class PlaceInfoDto {
        private Long id;
        private String name;
        private PointDto location;
    }


}
