package kr.co.yigil.travel.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CourseListDto extends ImageFileDto{

    private final Long courseId;
    private final String title;
    private final Double rate;
    private final String mapStaticImageUrl;
    private final Integer spotCount;
    private final LocalDateTime createdDate;
    private final Boolean isPrivate;

    @QueryProjection
    public CourseListDto(Long courseId, String title, Double rate, String mapStaticImageUrl,
        Integer spotCount, LocalDateTime createdDate, Boolean isPrivate) {
        this.courseId = courseId;
        this.title = title;
        this.rate = rate;
        this.mapStaticImageUrl = getImageUrl(mapStaticImageUrl);
        this.spotCount = spotCount;
        this.createdDate = createdDate;
        this.isPrivate = isPrivate;
    }
}
