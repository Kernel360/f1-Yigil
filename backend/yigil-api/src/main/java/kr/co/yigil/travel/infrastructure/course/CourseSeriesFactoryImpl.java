package kr.co.yigil.travel.infrastructure.course;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.domain.course.CourseCommand.ModifyCourseRequest;
import kr.co.yigil.travel.domain.course.CourseSeriesFactory;
import kr.co.yigil.travel.domain.spot.SpotReader;
import kr.co.yigil.travel.domain.spot.SpotSeriesFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseSeriesFactoryImpl implements CourseSeriesFactory {

    private final SpotSeriesFactory spotSeriesFactory;
    private final SpotReader spotReader;

    @Override
    public Course modify(ModifyCourseRequest command, Course course) {
        Map<Long, Spot> existingSpotMap = course.getSpots().stream()
                .collect(Collectors.toMap(Spot::getId, spot -> spot));

        List<Spot> modifiedSpots = command.getModifySpotRequests().stream()
                .map(request -> spotSeriesFactory.modify(request, spotReader.getSpot(request.getId())))
                .toList();

        List<Spot> sortedSpots = command.getSpotIdOrder().stream()
                .map(existingSpotMap::get)
                .collect(Collectors.toList());

        course.updateCourse(command.getDescription(), command.getRate(), sortedSpots);

        return course;
    }
}