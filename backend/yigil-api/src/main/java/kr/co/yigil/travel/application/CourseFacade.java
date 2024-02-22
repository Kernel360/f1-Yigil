package kr.co.yigil.travel.application;

import kr.co.yigil.file.FileUploader;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.course.CourseCommand.RegisterCourseRequest;
import kr.co.yigil.travel.domain.course.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseFacade {
    private final CourseService courseService;
    private final FileUploader fileUploader;

    public Slice<Course> getCourseSliceInPlace(Long placeId, Pageable pageable) {
        return courseService.getCoursesSliceInPlace(placeId, pageable);
    }

    public void registerCourse(RegisterCourseRequest command, Long memberId) {
        courseService.registerCourse(command, memberId);
        fileUploader.upload(command.getMapStaticImageFile());
    }

}
