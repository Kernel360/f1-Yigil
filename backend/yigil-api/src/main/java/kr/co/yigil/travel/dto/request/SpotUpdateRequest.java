package kr.co.yigil.travel.dto.request;

import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.dto.util.GeojsonConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class  SpotUpdateRequest {
    private String pointJson;
    private Boolean isInCourse;
    private String title;
    private String description;
    private MultipartFile file;

    public static Spot toEntity(Long spotId, SpotUpdateRequest spotUpdateRequest,String fileUrl) {
        return new Spot(
            spotId,
            GeojsonConverter.convertToPoint(spotUpdateRequest.getPointJson()),
            spotUpdateRequest.getIsInCourse(),
            spotUpdateRequest.getTitle(),
            spotUpdateRequest.getDescription(),
            fileUrl
            );
    }

}
