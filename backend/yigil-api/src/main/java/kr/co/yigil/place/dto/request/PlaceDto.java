package kr.co.yigil.place.dto.request;

import kr.co.yigil.file.AttachFile;
import kr.co.yigil.place.Place;
import kr.co.yigil.travel.dto.util.GeojsonConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceDto {

    private String name;
    private String address;
    private String imageUrl;
    private String pointJson;
    private AttachFile mapStaticImageFile;

    public static Place toEntity(String name, String address, String pointJson, String imageUrl, AttachFile mapStaticImageFile){
        return new Place(
                name,
                address,
                GeojsonConverter.convertToPoint(pointJson),
                imageUrl,
                mapStaticImageFile
        );
    }
}
