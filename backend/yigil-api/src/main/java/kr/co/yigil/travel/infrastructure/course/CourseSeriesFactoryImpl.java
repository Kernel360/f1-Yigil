package kr.co.yigil.travel.infrastructure.course;

import kr.co.yigil.file.AttachFile;
import kr.co.yigil.file.FileUploader;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.domain.course.CourseCommand.ModifyCourseRequest;
import kr.co.yigil.travel.domain.course.CourseSeriesFactory;
import kr.co.yigil.travel.domain.spot.SpotReader;
import kr.co.yigil.travel.domain.spot.SpotSeriesFactory;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CourseSeriesFactoryImpl implements CourseSeriesFactory {

    private final SpotSeriesFactory spotSeriesFactory;
    private final SpotReader spotReader;
    private final FileUploader fileUploader;

    @Override
    public Course modify(ModifyCourseRequest command, Course course) {
        List<Spot> sortedSpots = updateSpotsInCourse(command, course);

        AttachFile mapStaticImageFile = null;
        if(command.getMapStaticImage() != null)
            mapStaticImageFile = fileUploader.upload(command.getMapStaticImage());

        course.updateCourse(command.getTitle(), command.getDescription(), command.getRate(), command.getLineStringJson(), sortedSpots, mapStaticImageFile);
        return course;
    }

    private List<Spot> updateSpotsInCourse(ModifyCourseRequest command, Course course) {
        if (command.getSpotIdOrder() == null || command.getSpotIdOrder().isEmpty()) {
            throw new BadRequestException(ExceptionCode.INVALID_SPOT_ORDER_CHANGE_REQUEST);
        }

        List<Spot> updatedSpots = updateSpotOrderInCourse(command, course);

        updateSpotDetailsInCourse(command);

        return updatedSpots;
    }

    private void updateSpotDetailsInCourse(ModifyCourseRequest command) {
        if (command.getModifySpotRequests() != null)
            command.getModifySpotRequests()
                    .forEach(request -> spotSeriesFactory.modify(request, spotReader.getSpot(request.getId())));
    }

    @NotNull
    private List<Spot> updateSpotOrderInCourse(ModifyCourseRequest command, Course course) {
        List<Spot> updatedSpots = command.getSpotIdOrder().stream()
                .map(id -> {
                    Spot spot = spotReader.getSpot(id);
                    spot.changeInCourse();
                    return spot;
                })
                .toList();

        course.getSpots().stream()
                .filter(spot -> !updatedSpots.contains(spot))
                .forEach(Spot::changeOutOfCourse);
        return updatedSpots;
    }
}