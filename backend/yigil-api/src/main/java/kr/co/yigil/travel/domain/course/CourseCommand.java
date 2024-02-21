package kr.co.yigil.travel.domain.course;

import java.util.List;
import kr.co.yigil.travel.domain.spot.SpotCommand.RegisterSpotRequest;
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
    }

    @Getter
    @Builder
    @ToString
    public static class RegisterCourseRequestWithSpotInfo {

    }

}
