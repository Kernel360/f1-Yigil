package kr.co.yigil.place.dto.response;

import kr.co.yigil.place.Place;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceMapStaticImageResponse {
    private String uniquePlaceId;
    private String mapStaticImageUrl;

    public static PlaceMapStaticImageResponse from(Place place) {
        return new PlaceMapStaticImageResponse(
                place.getUniquePlaceId(),
                place.getMapStaticImageFile().getFileUrl()
        );
    }
}
