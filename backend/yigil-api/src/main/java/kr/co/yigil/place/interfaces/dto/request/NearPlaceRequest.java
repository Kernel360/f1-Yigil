package kr.co.yigil.place.interfaces.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NearPlaceRequest {
    private double minX;
    private double minY;
    private double maxX;
    private double maxY;
    private int page;
}
