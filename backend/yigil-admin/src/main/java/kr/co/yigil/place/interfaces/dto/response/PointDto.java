package kr.co.yigil.place.interfaces.dto.response;

import lombok.Data;

@Data
public class PointDto {

    private double x;
    private double y;
    public PointDto(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
