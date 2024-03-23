package kr.co.yigil.place.interfaces.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceMapResponse {
    private List<PlaceMapDto> places;

    @Data
    public static class PlaceMapDto {
        private Long id;
        private String name;
        private double x;
        private double y;
    }
}
