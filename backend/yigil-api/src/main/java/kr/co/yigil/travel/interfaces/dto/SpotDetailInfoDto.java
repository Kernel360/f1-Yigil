package kr.co.yigil.travel.interfaces.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SpotDetailInfoDto {
    private String placeName;
    private String rate;
    private String placeAddress;
    private String mapStaticImageFileUrl;
    private List<String> imageUrls;
    private String createDate;
    private String description;
}
