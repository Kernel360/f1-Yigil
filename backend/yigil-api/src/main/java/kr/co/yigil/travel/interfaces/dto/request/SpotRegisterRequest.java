package kr.co.yigil.travel.interfaces.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpotRegisterRequest {
    private String pointJson;
    private String description;
    private List<MultipartFile> files;
    private double rate;

    private MultipartFile mapStaticImageFile;
    private String placeName;
    private String placeAddress;
}
