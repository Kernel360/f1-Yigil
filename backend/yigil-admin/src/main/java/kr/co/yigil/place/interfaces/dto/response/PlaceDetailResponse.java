package kr.co.yigil.place.interfaces.dto.response;

import java.awt.Point;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDetailResponse {
    private Long id;
    private String name;
    private Point location;
    private String imageFileUrl;

}
