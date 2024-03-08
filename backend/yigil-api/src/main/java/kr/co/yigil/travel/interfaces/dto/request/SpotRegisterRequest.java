package kr.co.yigil.travel.interfaces.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpotRegisterRequest {
    private String pointJson;
    private String title;
    private String description;
    private List<MultipartFile> files;
    private double rate;

    private MultipartFile mapStaticImageFile;
    private MultipartFile placeImageFile;
    private String placeName;
    private String placeAddress;
    private String placePointJson;
}
