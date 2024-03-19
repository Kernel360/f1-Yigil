package kr.co.yigil.place.interfaces.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDetailResponse {
    private Long id;
    private String name;
    private String address;
    private LocalDateTime latestUploadedTime;
    private PointDto location;
    private String imageFileUrl;
    private String mapStaticImageFileUrl;
    private String regionName;
}
