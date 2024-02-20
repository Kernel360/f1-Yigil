package kr.co.yigil.travel.interfaces.dto.request;

import java.util.List;
import kr.co.yigil.file.AttachFiles;
import kr.co.yigil.member.Member;
import kr.co.yigil.place.Place;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.interfaces.dto.util.GeojsonConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class  SpotUpdateRequest {
    private String pointJson;
    private String title;
    private String description;
    private List<MultipartFile> files;
    private double rate;

    private Long placeId;


    public static Spot toEntity(Member member, Long spotId, SpotUpdateRequest spotUpdateRequest, Place place, AttachFiles attachFiles) {
        return new Spot(
                spotId,
                member,
                GeojsonConverter.convertToPoint(spotUpdateRequest.getPointJson()),
                false,
                spotUpdateRequest.getTitle(),
                spotUpdateRequest.getDescription(),
                attachFiles,
                place,
                spotUpdateRequest.getRate()
        );
    }

}
