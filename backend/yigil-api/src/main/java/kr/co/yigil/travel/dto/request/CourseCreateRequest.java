package kr.co.yigil.travel.dto.request;

import java.util.List;
import kr.co.yigil.file.AttachFile;
import kr.co.yigil.member.Member;
import kr.co.yigil.travel.Course;
import kr.co.yigil.travel.Spot;
import kr.co.yigil.travel.dto.util.GeojsonConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseCreateRequest {
    private String title;
    private String description;
    private double rate;
    private boolean isPrivate;
    private int representativeSpotOrder;
    private List<Long> spotIds;
    private String lineStringJson;
    private MultipartFile mapStaticImageFile;

    public static Course toEntity(Member member, CourseCreateRequest courseCreateRequest, List<Spot> spots, AttachFile attachFile) {
        return new Course(
            member,
            courseCreateRequest.getTitle(),
            courseCreateRequest.getDescription(),
            courseCreateRequest.getRate(),
            GeojsonConverter.convertToLineString(courseCreateRequest.getLineStringJson()),
            courseCreateRequest.isPrivate(),
                spots,
            courseCreateRequest.getRepresentativeSpotOrder(),
            attachFile
        );
    }
}
