package kr.co.yigil.place.dto.response;

import kr.co.yigil.place.Place;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlaceFindDto {
    private Long id;
    private String name;
    private String imageUrl;
    private int reviewCount;
    private double averageRate;


    public static PlaceFindDto from(Place place, int reviewCount, double averageRate) {
        return new PlaceFindDto(
                place.getId(),
                place.getName(),
                place.getImageFile().getFileUrl(),
                reviewCount,
                averageRate
        );
    }

}
