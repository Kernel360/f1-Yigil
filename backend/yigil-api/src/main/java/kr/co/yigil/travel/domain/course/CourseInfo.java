package kr.co.yigil.travel.domain.course;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.Spot;
import lombok.Getter;
import lombok.ToString;

public class CourseInfo {

    @Getter
    @ToString
    public static class Main {
        private final String title;
        private final double rate;
        private final String mapStaticImageUrl;
        private final String description;
        private final List<CourseSpotInfo> courseSpotList;

        public Main(Course course) {
            this.title = course.getTitle();
            this.rate = course.getRate();
            this.mapStaticImageUrl = course.getMapStaticImageFile().getFileUrl();
            this.description = course.getDescription();
            AtomicInteger index = new AtomicInteger(1);
            this.courseSpotList = course.getSpots().stream()
                    .map(spot -> new CourseSpotInfo(spot, index.getAndIncrement()))
                    .collect(Collectors.toList());
        }
    }

    @Getter
    @ToString
    public static class CourseSpotInfo {
        private final int order;
        private final String placeName;
        private final List<String> imageUrlList;
        private final double rate;
        private final String description;
        private final LocalDateTime createDate;

        public CourseSpotInfo(Spot spot, int index) {
            this.order = index;
            this.placeName = spot.getPlace().getName();
            this.imageUrlList = spot.getAttachFiles().getUrls();
            this.rate = spot.getRate();
            this.description = spot.getDescription();
            this.createDate = spot.getCreatedAt();
        }
    }

}
