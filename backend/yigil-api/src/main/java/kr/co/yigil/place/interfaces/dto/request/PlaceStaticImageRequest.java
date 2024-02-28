package kr.co.yigil.place.interfaces.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlaceStaticImageRequest {
    private String name;
    private String address;
}
