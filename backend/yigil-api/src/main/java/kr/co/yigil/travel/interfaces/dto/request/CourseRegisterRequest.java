package kr.co.yigil.travel.interfaces.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseRegisterRequest {
    private String title;
    private String description;
    private double rate;
    private boolean isPrivate;
    private int representativeSpotOrder;
    private String lineStringJson;
    private MultipartFile mapStaticImageFile;
    private List<SpotRegisterRequest> spotRegisterRequests;
}


