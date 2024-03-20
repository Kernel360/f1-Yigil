package kr.co.yigil.travel.infrastructure.course;

import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.domain.course.CourseCommand.ModifyCourseRequest;
import kr.co.yigil.travel.domain.course.CourseSeriesFactory;
import kr.co.yigil.travel.domain.spot.SpotReader;
import kr.co.yigil.travel.domain.spot.SpotSeriesFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CourseSeriesFactoryImpl implements CourseSeriesFactory {

    private final SpotSeriesFactory spotSeriesFactory;
    private final SpotReader spotReader;

    @Override
    public Course modify(ModifyCourseRequest command, Course course) {
        List<Spot> sortedSpots = updateSpotsInCourse(command, course);
        course.updateCourse(command.getTitle(), command.getDescription(), command.getRate(), sortedSpots);
        return course;
    }

    private List<Spot> updateSpotsInCourse(ModifyCourseRequest command, Course course) {
        if(command.getSpotIdOrder() == null || command.getSpotIdOrder().size() != course.getSpots().size()){
            throw new BadRequestException(ExceptionCode.INVALID_SPOT_ORDER_CHANGE_REQUEST);
        }

        Map<Long, Spot> existingSpotMap = course.getSpots().stream()
                .collect(Collectors.toMap(Spot::getId, spot -> spot));

        if(command.getModifySpotRequests() != null)
            command.getModifySpotRequests()
                    .forEach(request -> spotSeriesFactory.modify(request, spotReader.getSpot(request.getId())));

        return command.getSpotIdOrder().stream()
                .map(existingSpotMap::get)
                .collect(Collectors.toList());
    }
}