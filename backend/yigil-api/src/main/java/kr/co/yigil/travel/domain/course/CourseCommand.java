package kr.co.yigil.travel.domain.course;

import java.util.List;
import kr.co.yigil.file.FileUploadUtil;
import kr.co.yigil.member.Member;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.domain.spot.SpotCommand.RegisterSpotRequest;
import kr.co.yigil.travel.interfaces.dto.util.GeojsonConverter;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

public class CourseCommand {

    @Getter
    @Builder
    @ToString
    public static class RegisterCourseRequest {
        private String title;
        private String description;
        private double rate;
        private boolean isPrivate;
        private int representativeSpotOrder;
        private String lineStringJson;
        private MultipartFile mapStaticImageFile;
        private List<RegisterSpotRequest> registerSpotRequests;

        public Course toEntity(List<Spot> spots, Member member) {
            var attachFile = FileUploadUtil.predictAttachFile(mapStaticImageFile);
            return new Course(
                    member,
                    title,
                    description,
                    rate,
                    GeojsonConverter.convertToLineString(lineStringJson),
                    isPrivate,
                    spots,
                    representativeSpotOrder,
                    attachFile
                    );
        }
    }

    @Getter
    @Builder
    @ToString
    public static class RegisterCourseRequestWithSpotInfo {
        private String title;
        private String description;
        private double rate;
        private boolean isPrivate;
        private int representativeSpotOrder;
        private String lineStringJson;
        private MultipartFile mapStaticImageFile;
        private List<Long> spotIds;
    }

}
