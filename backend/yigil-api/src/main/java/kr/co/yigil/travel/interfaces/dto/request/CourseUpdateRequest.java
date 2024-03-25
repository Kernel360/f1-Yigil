package kr.co.yigil.travel.interfaces.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseUpdateRequest {
    private String description;
    private double rate;
    private String title;
    private String lineStringJson;
    private MultipartFile mapStaticImage;
    private List<Long> spotIdOrder;
    private List<SpotUpdateRequest> courseSpotUpdateRequests;
}