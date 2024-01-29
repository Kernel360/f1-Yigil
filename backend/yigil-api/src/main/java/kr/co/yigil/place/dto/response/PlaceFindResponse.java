package kr.co.yigil.place.dto.response;

import kr.co.yigil.place.Place;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceFindResponse {

    private String name;
    private String address;
    private String imageUrl;

    public static PlaceFindResponse from(Place place) {
        return new PlaceFindResponse(
                place.getName(),
                place.getAddress(),
                place.getImageUrl());
    }
}
