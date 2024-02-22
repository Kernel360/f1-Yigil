package kr.co.yigil.travel.interfaces.dto.request;

import java.util.List;
import kr.co.yigil.file.AttachFile;
import kr.co.yigil.member.Member;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.interfaces.dto.util.GeojsonConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseUpdateRequest {
    private String description;
    private double rate;
    private List<Long> spotIdOrder;
    private List<SpotUpdateRequest> courseSpotUpdateRequests;
}