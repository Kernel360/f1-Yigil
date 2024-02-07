package kr.co.yigil.place.dto.response;

import kr.co.yigil.place.Place;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceInfoResponse {
    private String name;
    private String address;
    private String imageUrl;
    private String mapStaticImageUrl;
    private int reviewCount;
    private double averageRate;



    public static PlaceInfoResponse from(Place place, int reviewCount, double averageRate){
        return new PlaceInfoResponse(
                place.getName(),
                place.getAddress(),
                place.getImageUrl(),
                place.getMapStaticImageFile().getFileUrl(),
                reviewCount,
                averageRate
        );
    }
}
