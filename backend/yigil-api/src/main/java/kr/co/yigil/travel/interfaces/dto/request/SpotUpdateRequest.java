package kr.co.yigil.travel.interfaces.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpotUpdateRequest {
    private Long id;
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
