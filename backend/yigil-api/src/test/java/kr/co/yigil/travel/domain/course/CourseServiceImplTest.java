package kr.co.yigil.travel.domain.course;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import kr.co.yigil.file.AttachFile;
import kr.co.yigil.global.exception.AuthException;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.domain.MemberReader;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.domain.course.CourseCommand.ModifyCourseRequest;
import kr.co.yigil.travel.domain.course.CourseCommand.RegisterCourseRequest;
import kr.co.yigil.travel.domain.course.CourseInfo.Main;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

@ExtendWith(MockitoExtension.class)
public class CourseServiceImplTest {

    @Mock
    private MemberReader memberReader;
    @Mock
    private CourseReader courseReader;
    @Mock
    private CourseStore courseStore;
    @Mock
    private CourseSeriesFactory courseSeriesFactory;
    @Mock
    private CourseSpotSeriesFactory courseSpotSeriesFactory;

    @InjectMocks
    private CourseServiceImpl courseService;

    @DisplayName("getCoursesSliceInPlace 메서드가 Course의 Slice를 잘 반환하는지")
    @Test
    void getCoursesSliceInPlace_ReturnsSlice() {
        Long placeId = 1L;
        Pageable pageable = PageRequest.of(0, 10);
        Slice<Course> expectedSlice = mock(Slice.class);

        when(courseReader.getCoursesSliceInPlace(placeId, pageable)).thenReturn(expectedSlice);

        Slice<Course> result = courseService.getCoursesSliceInPlace(placeId, pageable);

        assertEquals(expectedSlice, result);
        verify(courseReader).getCoursesSliceInPlace(placeId, pageable);
    }

    @DisplayName("registerCourse 메서드가 Course를 잘 생성하는지")
    @Test
    void registerCourse_storeCourse() {
        Long memberId = 1L;
        RegisterCourseRequest command = mock(RegisterCourseRequest.class);
        Member member = mock(Member.class);
        List<Spot> spots = new ArrayList<>();
        Course course = mock(Course.class);

        when(memberReader.getMember(memberId)).thenReturn(member);
        when(courseSpotSeriesFactory.store(command, memberId)).thenReturn(spots);
        when(command.toEntity(spots, member)).thenReturn(course);

        courseService.registerCourse(command, memberId);

        verify(courseStore).store(any(Course.class));
    }

    @DisplayName("retrieveCourseInfo 메서드가 CourseInfo를 잘 반환하는지")
    @Test
    void retrieveCourseInfo_ShouldReturnCourseInfo() {
        Long courseId = 1L;
        Course course = mock(Course.class);
        AttachFile attachFile = mock(AttachFile.class);
        when(courseReader.getCourse(courseId)).thenReturn(course);
        when(course.getMapStaticImageFile()).thenReturn(attachFile);
        Main result = courseService.retrieveCourseInfo(courseId);

        assertNotNull(result);
        verify(courseReader).getCourse(courseId);
    }

    @DisplayName("modifyCourse 메서드가 유효한 memberId가 있을 때 엔티티를 잘 수정하는지")
    @Test
    void modifyCourse_WithValidMemberId_ModifiesCourse() {
        Long courseId = 1L, memberId = 1L;
        ModifyCourseRequest command = mock(ModifyCourseRequest.class);
        Course course = mock(Course.class);
        Member member = mock(Member.class);

        when(course.getMember()).thenReturn(member);
        when(member.getId()).thenReturn(memberId);
        when(courseReader.getCourse(courseId)).thenReturn(course);

        courseService.modifyCourse(command, courseId, memberId);

        verify(courseSeriesFactory).modify(command, course);
    }

    @DisplayName("modifyCourse 메서드가 유효하지 않은 memberId가 있을 때 예외를 잘 발생시키는지")
    @Test
    void modifyCourse_WithInvalidMemberId_ThrowsAuthException() {
        Long courseId = 1L, memberId = 2L;
        ModifyCourseRequest command = mock(ModifyCourseRequest.class);
        Course course = mock(Course.class);
        Member member = mock(Member.class);

        when(course.getMember()).thenReturn(member);
        when(member.getId()).thenReturn(1L);
        when(courseReader.getCourse(courseId)).thenReturn(course);

        assertThrows(AuthException.class, () -> courseService.modifyCourse(command, courseId, memberId));
    }

    @DisplayName("deleteCourse 메서드가 유효한 memberId가 있을 때 잘 삭제시키는지")
    @Test
    void deleteCourse_WithValidMemberId_DeleteCourse() {
        Long courseId = 1L, memberId = 1L;
        Course course = mock(Course.class);
        Member member = mock(Member.class);

        when(course.getMember()).thenReturn(member);
        when(member.getId()).thenReturn(memberId);
        when(courseReader.getCourse(courseId)).thenReturn(course);

        courseService.deleteCourse(courseId, memberId);

        verify(courseStore).remove(course);
    }

    @DisplayName("deleteCourse 메서드가 유효하지 않은 memberId가 있을 때 예외를 잘 발생시키는지")
    @Test
    void deleteCourse_WithInvalidMemberId_ShouldThrowAuthException() {
        Long courseId = 1L, memberId = 2L;
        Course course = mock(Course.class);
        Member member = mock(Member.class);

        when(course.getMember()).thenReturn(member);
        when(member.getId()).thenReturn(1L);
        when(courseReader.getCourse(courseId)).thenReturn(course);

        assertThrows(AuthException.class, () -> courseService.deleteCourse(courseId, memberId));
    }
}