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

    private String title;
    private String description;
    private double rate;
    private boolean isPrivate;
    private int representativeSpotOrder;

    private String lineStringJson;
    private MultipartFile mapStaticImageFile;
    private List<Long> spotIds;
    private List<Long> removedSpotIds;
    private List<Long> addedSpotIds;

    public static Course toEntity(Member member, Long courseId,
        CourseUpdateRequest courseUpdateRequest, List<Spot> spots, AttachFile attachFile) {
        return new Course(
            courseId,
            member,
            courseUpdateRequest.getTitle(),
            courseUpdateRequest.getDescription(),
            courseUpdateRequest.getRate(),
            GeojsonConverter.convertToLineString(courseUpdateRequest.getLineStringJson()),
            courseUpdateRequest.isPrivate(),
            spots,
            courseUpdateRequest.getRepresentativeSpotOrder(),
            attachFile
        );
    }
}