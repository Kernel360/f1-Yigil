package kr.co.yigil.travel.course.domain;

import kr.co.yigil.comment.domain.CommentReader;
import kr.co.yigil.favor.domain.FavorReader;
import kr.co.yigil.file.AttachFile;
import kr.co.yigil.member.Member;
import kr.co.yigil.place.domain.Place;
import kr.co.yigil.travel.course.domain.CourseInfoDto.CoursesPageInfo;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.Spot;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Point;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @Mock
    private CourseReader courseReader;
    @Mock
    private CourseStore courseStore;
    @Mock
    private FavorReader favorReader;
    @Mock
    private CommentReader commentReader;
    @InjectMocks
    private CourseServiceImpl courseServiceImpl;

    @DisplayName("getCourses 메서드가 CourseReader를 잘 호출하는지")
    @Test
    void whenGetCourses_thenShouldReturnCoursesPageInfo() {
        // Arrange
        PageRequest pageRequest = PageRequest.of(0, 10);
        Course course = mock(Course.class);
        Member member = mock(Member.class);
        when(course.getMember()).thenReturn(member);
        when(member.getNickname()).thenReturn("nickname");
        List<Course> courses = List.of(course);
        Page<Course> pageCourses = new PageImpl<>(courses, pageRequest, courses.size());
        when(courseReader.getCourses(any(PageRequest.class))).thenReturn(pageCourses);

        CourseInfoDto.CourseAdditionalInfo additionalInfo = new CourseInfoDto.CourseAdditionalInfo(
            1, 1);
        when(favorReader.getFavorCount(any(Long.class))).thenReturn(additionalInfo.getFavorCount());
        when(commentReader.getCommentCount(any(Long.class))).thenReturn(
            additionalInfo.getCommentCount());

        CoursesPageInfo result = courseServiceImpl.getCourses(pageRequest);

        assertEquals(courses.size(), result.getCourses().getContent().size());
        assertEquals(pageCourses.getTotalElements(), result.getCourses().getTotalElements());
        assertEquals(pageCourses.getPageable(), result.getCourses().getPageable());
    }

    @DisplayName("getCourse 메서드가 CourseReader를 잘 호출하는지")
    @Test
    void getCourse() {
        Long courseId = 1L;
        Long spotId = 2L;
        Member member = mock(Member.class);
        Point point = mock(Point.class);
        when(point.getX()).thenReturn(1.0);
        when(point.getY()).thenReturn(1.0);

        Spot spot1 = mock(Spot.class);
        Place place1 = mock(Place.class);
        when(spot1.getId()).thenReturn(spotId);
        when(spot1.getPlace()).thenReturn(place1);
        when(spot1.getLocation()).thenReturn(point);

        AttachFile mockAttachFile = new AttachFile(null, "url", "filename", 4L);
        Course course = new Course(courseId, member, "title", "content", 3.5, null, false, List.of(spot1), 1, mockAttachFile);
        when(courseReader.getCourse(courseId)).thenReturn(course);

        CourseInfoDto.CourseAdditionalInfo additionalInfo = new CourseInfoDto.CourseAdditionalInfo(1, 1);
        when(favorReader.getFavorCount(any(Long.class))).thenReturn(additionalInfo.getFavorCount());

        var result = courseServiceImpl.getCourse(courseId);
        assertEquals(course.getId(), result.getCourseId());
    }


    @DisplayName("deleteCourse 메서드가 CourseStore를 잘 호출하는지")
    @Test
    void deleteCourse() {
        Long courseId = 1L;
        Member member = mock(Member.class);
        AttachFile mockAttachFile = new AttachFile(null, "url", "filename", 4L);
        Course course = new Course(courseId, member, "title", "content", 3.5, null, false, null, 1, mockAttachFile);
        when(courseReader.getCourse(courseId)).thenReturn(course);

        var result = courseServiceImpl.deleteCourse(courseId);
        assertEquals(member.getId(), result);
        verify(courseStore).deleteCourse(course);
    }
}