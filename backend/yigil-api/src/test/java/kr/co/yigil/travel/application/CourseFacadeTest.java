package kr.co.yigil.travel.application;

import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.file.FileUploader;
import kr.co.yigil.global.Selected;
import kr.co.yigil.member.*;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.course.CourseCommand.ModifyCourseRequest;
import kr.co.yigil.travel.domain.course.CourseCommand.RegisterCourseRequest;
import kr.co.yigil.travel.domain.course.CourseCommand.RegisterCourseRequestWithSpotInfo;
import kr.co.yigil.travel.domain.course.CourseInfo;
import kr.co.yigil.travel.domain.course.CourseService;
import kr.co.yigil.travel.domain.dto.CourseListDto;
import kr.co.yigil.travel.domain.spot.SpotService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseFacadeTest {

    @InjectMocks
    private CourseFacade courseFacade;

    @Mock
    private CourseService courseService;

    @Mock
    private SpotService spotService;

    @Mock
    private FileUploader fileUploader;


    //todo: CourseFacadeTest 작성하기

    @DisplayName("getCourseSliceInPlace 메서드가 유효한 요청이 들어왔을 때 Course의 Slice객체를 잘 반환하는지")
    @Test
    void whenGetCoursesSliceInPlace_WithValidRequest() {
        Long memberId = 1L;
//        String email = "test@test.com";
//        String socialLoginId = "12345";
//        String nickname = "tester";
//        String profileImageUrl = "test.jpg";
//        Member member = new Member(id, email, socialLoginId, nickname, profileImageUrl,
//                SocialLoginType.KAKAO, Ages.NONE, Gender.NONE);

//        String title = "Test Course Title";
//        String description = "Test Course Description";
//        double rate = 5.0;
//        LineString path = null;
//        boolean isPrivate = false;
//        List<Spot> spots = Collections.emptyList();
//        int representativeSpotOrder = 0;
//        AttachFile mapStaticImageFile = null;
//
//        Course course = new Course(id, member, title, description, rate, path, isPrivate, spots,
//                representativeSpotOrder, mapStaticImageFile);

        Long placeId = 1L;
        Pageable pageable = PageRequest.of(0, 10);
        CourseInfo.CoursesInPlaceResponseInfo mockResponse = mock(CourseInfo.CoursesInPlaceResponseInfo.class);

        when(courseService.getCoursesSliceInPlace(eq(placeId), anyLong(),any(Pageable.class))).thenReturn(
                mockResponse);

        var result = courseFacade.getCourseSliceInPlace(placeId, memberId,  pageable);

        assertNotNull(result);
        assertEquals(mockResponse, result);
        verify(courseService, times(1)).getCoursesSliceInPlace(eq(placeId), eq(memberId), any(Pageable.class));
    }

    @DisplayName("registerCourse 메서드가 CourseServicer를 잘 호출하는지")
    @Test
    void registerCourse_ShouldCallService() {
        RegisterCourseRequest command = mock(RegisterCourseRequest.class);
        Long memberId = 1L;

        courseFacade.registerCourse(command, memberId);

        verify(courseService).registerCourse(command, memberId);
    }

    @DisplayName("registerCourseWithoutSeries 메서드가 CourseService를 잘 호출하는지")
    @Test
    void registerCourseWithoutSeries_ShouldCallServiceAndUploader() {
        RegisterCourseRequestWithSpotInfo command = mock(RegisterCourseRequestWithSpotInfo.class);
        Long memberId = 1L;

        courseFacade.registerCourseWithoutSeries(command, memberId);

        verify(courseService).registerCourseWithoutSeries(command, memberId);
    }

    @DisplayName("retrieveCourseInfo 메서드가 CourseInfo를 잘 반환하는지")
    @Test
    void retrieveCourseInfo_ShoudReturnCourseInfo() {
        Long courseId = 1L;
        CourseInfo.Main expectedCourseInfo = mock(CourseInfo.Main.class);

        when(courseService.retrieveCourseInfo(courseId)).thenReturn(expectedCourseInfo);

        CourseInfo.Main result = courseFacade.retrieveCourseInfo(courseId);

        assertEquals(expectedCourseInfo, result);
        verify(courseService).retrieveCourseInfo(courseId);
    }

    @DisplayName("modifyCourse 메서드가 CourseService를 잘 호출하는지")
    @Test
    void modifyCourse_ShouldCallService() {
        ModifyCourseRequest command = mock(ModifyCourseRequest.class);
        Long courseId = 1L;
        Long memberId = 1L;
        Course mockCourse = mock(Course.class);
        when(courseService.modifyCourse(command, courseId, memberId)).thenReturn(mockCourse);

        courseFacade.modifyCourse(command, courseId, memberId);

        verify(courseService).modifyCourse(command, courseId, memberId);
    }

    @DisplayName("deleteCourse 메서드가 CourseSerivce를 잘 호출하는지")
    @Test
    void deleteCourse_ShouldCallService() {
        Long courseId = 1L;
        Long memberId = 1L;

        doNothing().when(courseService).deleteCourse(courseId, memberId);

        courseFacade.deleteCourse(courseId, memberId);

        verify(courseService).deleteCourse(courseId, memberId);
    }


    @DisplayName("getMemberCourseInfo 메서드가 유효한 요청이 들어왔을 때 CourseInfo의 MyCoursesResponse 객체를 잘 반환하는지")
    @Test
    void WhenGetMemberCourseInfo_ThenShouldReturnValidMyCoursesResponse() {
        // Given
        Long memberId = 1L;
        int totalPages = 1;
        PageRequest pageable = PageRequest.of(0, 5);

        String email = "test@test.com";
        String socialLoginId = "12345";
        String nickname = "tester";
        String profileImageUrl = "test.jpg";
        Member member = new Member(memberId, email, socialLoginId, nickname, profileImageUrl,
                SocialLoginType.KAKAO, Ages.NONE, Gender.NONE);

        Long courseId = 1L;
        String title = "Test Course Title";
        double rate = 5.0;
        boolean isPrivate = false;
        int numberOfSpots = 3;
        String imageUrl = "test.jpg";

        CourseListDto mockCourse = new CourseListDto(courseId, title, rate, imageUrl,
                numberOfSpots, null, isPrivate);

        CourseInfo.CourseListInfo courseInfo = new CourseInfo.CourseListInfo(mockCourse);
        List<CourseInfo.CourseListInfo> courseList = Collections.singletonList(courseInfo);

        CourseInfo.MyCoursesResponse mockCourseListResponse = new CourseInfo.MyCoursesResponse(
                courseList,
                totalPages
        );

        when(courseService.retrieveCourseList(anyLong(), any(Pageable.class),
                any(Selected.class))).thenReturn(
                mockCourseListResponse);

        // When
        var result = courseFacade.getMemberCoursesInfo(memberId, pageable, Selected.ALL);

        // Then
        assertThat(result).isNotNull()
                .isInstanceOf(CourseInfo.MyCoursesResponse.class)
                .usingRecursiveComparison().isEqualTo(mockCourseListResponse);
        assertThat(result.getContent().size()).isEqualTo(1);
    }

    @DisplayName("searchCourseByPlaceName 메서드가 유효한 요청이 들어왔을 때 CourseInfo의 Slice 객체를 잘 반환하는지")
    @Test
    void WhenSearchCourseByPlaceName_ThenShouldReturnValidSlice() {
        CourseInfo.Slice mockSlice = mock(CourseInfo.Slice.class);
        when(courseService.searchCourseByPlaceName(anyString(), any(Accessor.class), any(Pageable.class))).thenReturn(mockSlice);

        var result = courseFacade.searchCourseByPlaceName("test", mock(Accessor.class), PageRequest.of(0, 5));

        assertThat(result).isNotNull();
    }

    @DisplayName("getMySpotsDetailInfo 메서드가 유효한 요청이 들어왔을 때 CourseInfo의 MySpotsInfo 객체를 잘 반환하는지")
    @Test
    void whenGetMySpotsDetailInfo_thenShouldReturnMySpotsInfo() {
        CourseInfo.MySpotsInfo spotsInfo = mock(CourseInfo.MySpotsInfo.class);
        when(spotService.getMySpotsDetailInfo(anyList(), anyLong())).thenReturn(spotsInfo);

        var result = courseFacade.getMySpotsDetailInfo(List.of(1L, 2L), 1L);

        assertThat(result).isInstanceOf(CourseInfo.MySpotsInfo.class);
    }

    @DisplayName("getFavoriteCoursesInfo 메서드가 유효한 요청이 들어왔을 때 CourseInfo의 MyFavoriteCoursesInfo 객체를 잘 반환하는지")
    @Test
    void getFavoriteCoursesInfo() {
        Long memberId = 1L;
        int totalPages = 1;
        PageRequest pageable = PageRequest.of(0, 5);

        CourseInfo.MyFavoriteCoursesInfo mockFavoriteCoursesInfo = mock(CourseInfo.MyFavoriteCoursesInfo.class);

        when(courseService.getFavoriteCoursesInfo(eq(memberId), any(PageRequest.class))).thenReturn(mockFavoriteCoursesInfo);

        var result = courseFacade.getFavoriteCoursesInfo(memberId, pageable);

        assertThat(result).isNotNull()
                .isInstanceOf(CourseInfo.MyFavoriteCoursesInfo.class)
                .usingRecursiveComparison().isEqualTo(mockFavoriteCoursesInfo);
    }
}
