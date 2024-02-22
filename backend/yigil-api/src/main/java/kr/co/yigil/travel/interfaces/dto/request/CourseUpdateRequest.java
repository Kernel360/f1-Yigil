package kr.co.yigil.travel.interfaces.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseUpdateRequest {
    private String description;
    private double rate;
    private List<Long> spotIdOrder;
    private List<SpotUpdateRequest> courseSpotUpdateRequests;
}