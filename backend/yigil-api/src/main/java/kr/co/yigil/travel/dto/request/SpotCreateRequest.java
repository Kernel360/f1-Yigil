package kr.co.yigil.travel.dto.request;

import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.dto.util.GeojsonConverter;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
    private MultipartFile file;

    public static Spot toEntity(SpotCreateRequest spotCreateRequest,String fileUrl) {
        return new Spot(
            GeojsonConverter.convertToPoint(spotCreateRequest.getPointJson()),
            spotCreateRequest.getTitle(),
            spotCreateRequest.getDescription(),
            fileUrl
        );
    }
}
