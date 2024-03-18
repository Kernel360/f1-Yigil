package kr.co.yigil.place.interfaces.dto;

import java.awt.Point;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceInfoDto {
    private Long id;
    private String name;
    private Point location;
}
