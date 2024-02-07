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
    private String address;
    private String imageUrl;
    private int spotCount;


    public static PlaceFindDto from(Place place, int spotCount) {
        return new PlaceFindDto(
                place.getId(),
                place.getName(),
                place.getAddress(),
                place.getImageUrl(),
                spotCount
        );
    }

}
