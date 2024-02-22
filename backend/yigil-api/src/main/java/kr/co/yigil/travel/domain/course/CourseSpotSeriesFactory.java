package kr.co.yigil.travel.domain.course;

import java.util.List;
import kr.co.yigil.travel.domain.Spot;

public interface CourseSpotSeriesFactory {
    List<Spot> store(CourseCommand.RegisterCourseRequest request, Long memberId);
}
