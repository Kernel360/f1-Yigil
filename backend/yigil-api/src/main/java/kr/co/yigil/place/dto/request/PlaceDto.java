package kr.co.yigil.place.dto.request;

import kr.co.yigil.file.AttachFile;
import kr.co.yigil.place.Place;
import kr.co.yigil.travel.interfaces.dto.util.GeojsonConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceDto {

    private String name;
    private String address;
    private String pointJson;
    private AttachFile imageFile;
    private AttachFile mapStaticImageFile;

    public static Place toEntity(String name, String address, String pointJson, AttachFile imageFile, AttachFile mapStaticImageFile){
        return new Place(
                name,
                address,
                GeojsonConverter.convertToPoint(pointJson),
                imageFile,
                mapStaticImageFile
        );
    }
}
