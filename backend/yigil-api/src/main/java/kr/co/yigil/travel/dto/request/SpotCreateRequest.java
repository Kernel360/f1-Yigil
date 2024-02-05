package kr.co.yigil.travel.dto.request;


import java.util.List;
import kr.co.yigil.file.AttachFiles;
import kr.co.yigil.member.Member;
import kr.co.yigil.place.Place;
import kr.co.yigil.travel.Spot;
import kr.co.yigil.travel.dto.util.GeojsonConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpotCreateRequest {

    private String pointJson;
    private String title;
    private String description;
    private List<MultipartFile> files;
    private double rate;

    private MultipartFile mapStaticImageFile;
    private String placeImageUrl;
    private String uniquePlaceId;
    private String placeName;
    private String placeAddress;
    private String placePointJson;

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
        );
    }
}
