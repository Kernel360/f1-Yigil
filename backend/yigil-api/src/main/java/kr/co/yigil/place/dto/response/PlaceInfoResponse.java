package kr.co.yigil.place.dto.response;

import kr.co.yigil.place.Place;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceInfoResponse {
    private String uniquePlaceId;
    private String name;
    private String address;
    private String imageUrl;
    private String mapStaticImageUrl;

    public static PlaceInfoResponse from(Place place) {
        return new PlaceInfoResponse(
                place.getUniquePlaceId(),
                place.getName(),
                place.getAddress(),
                place.getImageUrl(),
                place.getMapStaticImageFile().getFileUrl()
        );
    }
}
