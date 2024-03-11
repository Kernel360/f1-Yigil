package kr.co.yigil.place.interfaces.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlaceStaticImageResponse {
    private boolean exists;
    private String mapStaticImageUrl;
    private boolean registeredPlace;
}
