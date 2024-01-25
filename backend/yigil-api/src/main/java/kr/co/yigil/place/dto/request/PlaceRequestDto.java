package kr.co.yigil.place.dto.request;

import kr.co.yigil.place.Place;
import kr.co.yigil.travel.dto.util.GeojsonConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceRequestDto {
    private String name;
    private String address;
    private String pointJson;

    public static Place toEntity(PlaceRequestDto placeRequestDto) {
        return new Place(
            placeRequestDto.getName(),
            placeRequestDto.getAddress(),
            GeojsonConverter.convertToPoint(placeRequestDto.getPointJson()),
            null
        );
    }
}
