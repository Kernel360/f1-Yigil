package kr.co.yigil.place.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlaceCoordinateDto {
    private Long id;
    private double x;
    private double y;
    private String placeName;
    private boolean isAlreadyUsed;
}
