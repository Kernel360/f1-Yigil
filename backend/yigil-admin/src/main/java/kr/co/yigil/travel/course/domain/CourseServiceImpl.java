package kr.co.yigil.travel.course.domain;

import kr.co.yigil.comment.domain.CommentReader;
import kr.co.yigil.favor.domain.FavorReader;
import kr.co.yigil.travel.course.domain.CourseInfoDto.CourseAdditionalInfo;
import kr.co.yigil.travel.course.domain.CourseInfoDto.CourseDetailInfo;
import kr.co.yigil.travel.course.domain.CourseInfoDto.CourseListUnit;
import kr.co.yigil.travel.course.domain.CourseInfoDto.CoursesPageInfo;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.Spot;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseReader courseReader;
    private final CourseStore courseStore;
    private final FavorReader favorReader;
    private final CommentReader commentReader;

    @Override
    @Transactional(readOnly = true)
    public CoursesPageInfo getCourses(PageRequest pageRequest) {
        Page<Course> courses = courseReader.getCourses(pageRequest);
        List<Course> courseList = courses.getContent();
        var courseListUnits = courseList.stream()
                .map(this::getCourseListUnit)
                .toList();

        return new CoursesPageInfo(
                courseListUnits, courses.getPageable(), courses.getTotalElements()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public CourseDetailInfo getCourse(Long courseId) {
        Course course = courseReader.getCourse(courseId);
        List<Spot> spots = course.getSpots();
        List<CourseInfoDto.SpotDetailInfo> spotDetailInfos = spots.stream()
                .map(this::getSpotDetailInfo).toList();

        return new CourseDetailInfo(course, getAdditionalInfo(courseId), spotDetailInfos);
    }

    @Override
    @Transactional
    public Long deleteCourse(Long courseId) {
        Course course = courseReader.getCourse(courseId);
        courseStore.deleteCourse(course);
        return course.getMember().getId();
    }

    private CourseAdditionalInfo getAdditionalInfo(Long courseId) {
        int favorCount = favorReader.getFavorCount(courseId);
        int commentCount = commentReader.getCommentCount(courseId);
        return new CourseAdditionalInfo(favorCount, commentCount);
    }

    private CourseListUnit getCourseListUnit(Course course) {
        return new CourseListUnit(course, getAdditionalInfo(course.getId()));
    }

    private CourseInfoDto.SpotDetailInfo getSpotDetailInfo(Spot spot) {
        return new CourseInfoDto.SpotDetailInfo(
                spot,
                favorReader.getFavorCount(spot.getId()),
                commentReader.getCommentCount(spot.getId())
        );
    }
}
