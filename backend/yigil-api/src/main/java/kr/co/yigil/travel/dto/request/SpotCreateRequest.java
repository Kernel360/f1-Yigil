package kr.co.yigil.travel.dto.request;

<<<<<<< Updated upstream
=======
import java.util.List;
import kr.co.yigil.File.AttachFiles;
import kr.co.yigil.member.Member;
import kr.co.yigil.place.Place;
import kr.co.yigil.place.dto.request.PlaceRequestDto;
>>>>>>> Stashed changes
import kr.co.yigil.travel.Spot;
import kr.co.yigil.travel.dto.util.GeojsonConverter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpotCreateRequest {

    private String pointJson;
    private String title;
    private String description;
    private List<MultipartFile> files;
    private PlaceRequestDto placeRequestDto;
    private double rate;

<<<<<<< Updated upstream
    public static Spot toEntity(SpotCreateRequest spotCreateRequest) {
        return new Spot(
            GeojsonConverter.convertToPoint(spotCreateRequest.getPointJson()),
            spotCreateRequest.getTitle(),
            spotCreateRequest.getDescription(),
                null
=======
    public static Spot toEntity(Member member, Place place, SpotCreateRequest spotCreateRequest, AttachFiles attachFiles) {
        return new Spot(
                member,
                GeojsonConverter.convertToPoint(spotCreateRequest.getPointJson()),
                false,
                spotCreateRequest.getTitle(),
                spotCreateRequest.getDescription(),
                attachFiles,
                place,
                spotCreateRequest.getRate()
>>>>>>> Stashed changes
        );
    }
}
