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
public class SpotUpdateRequest {

    private String description;
    private double rate;
    List<OriginalSpotImage> originalSpotImages;
    List<UpdateSpotImage> updateSpotImages;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OriginalSpotImage {
        private String imageUrl;
        private int index;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateSpotImage {
        private MultipartFile imageFile;
        private int index;
    }

}
