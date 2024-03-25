package kr.co.yigil.travel.domain.course;

import kr.co.yigil.file.AttachFile;
import kr.co.yigil.member.Member;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.domain.spot.SpotCommand.ModifySpotRequest;
import kr.co.yigil.travel.domain.spot.SpotCommand.RegisterSpotRequest;
import kr.co.yigil.travel.util.GeojsonConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.locationtech.jts.geom.LineString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

        public Course toEntity(List<Spot> spots, Member member, AttachFile attachFile) {
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

        public Course toEntity(List<Spot> spots, Member member, AttachFile attachFile) {
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
    @AllArgsConstructor
    public static class ModifyCourseRequest {
        private String description;
        private double rate;
        private String title;
        private LineString lineStringJson;
        private MultipartFile mapStaticImage;
        private List<Long> spotIdOrder;
        private List<ModifySpotRequest> modifySpotRequests;
    }

}
