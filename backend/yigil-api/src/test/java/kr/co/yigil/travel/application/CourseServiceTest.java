package kr.co.yigil.travel.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import kr.co.yigil.comment.application.CommentRedisIntegrityService;
import kr.co.yigil.comment.application.CommentService;
import kr.co.yigil.comment.domain.CommentCount;
import kr.co.yigil.comment.dto.response.CommentResponse;
import kr.co.yigil.favor.application.FavorRedisIntegrityService;
import kr.co.yigil.favor.domain.FavorCount;
import kr.co.yigil.file.AttachFiles;
import kr.co.yigil.member.Member;
import kr.co.yigil.member.application.MemberService;
import kr.co.yigil.place.Place;
import kr.co.yigil.travel.Course;
import kr.co.yigil.travel.Spot;
import kr.co.yigil.travel.dto.request.CourseCreateRequest;
import kr.co.yigil.travel.dto.request.CourseUpdateRequest;
import kr.co.yigil.travel.dto.response.CourseCreateResponse;
import kr.co.yigil.travel.dto.response.CourseDeleteResponse;
import kr.co.yigil.travel.dto.response.CourseInfoResponse;
import kr.co.yigil.travel.dto.response.CourseListResponse;
import kr.co.yigil.travel.dto.response.CourseUpdateResponse;
import kr.co.yigil.travel.repository.CourseRepository;
import kr.co.yigil.travel.repository.SpotRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @InjectMocks
    private CourseService courseService;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private MemberService memberService;

    @Mock
    private SpotService spotService;

    @Mock
    private FavorRedisIntegrityService favorRedisIntegrityService;
    @Mock
    private CommentRedisIntegrityService commentRedisIntegrityService;
    @Mock
    private CommentService commentService;
    @Mock
    private SpotRepository spotRepository;

    @DisplayName("getCourseList 메서드가 유효한 인자를 넘겨받았을 때 올바른 응답을 내리는지")
    @Test
    void GivenValidInput_WhenGetCourseListTest_ThenReturnCourseFindListResponse() {
        // Arrange
        Long placeId = 1L;

        List<Course> mockCourses = new ArrayList<>();
        Long courseId = 1L;
        mockCourses.add(
                new Course(new Member("shin@gmail.com", "123456", "똷", "profile.jpg", "kakao"),
                        courseId, new GeometryFactory().createLineString(
                        new Coordinate[]{new Coordinate(127.123456, 37.123456),
                                new Coordinate(127.123456, 37.123456)}), new ArrayList<>(), 0,
                        "mockTitle"));
        when(courseRepository.findBySpotPlaceId(placeId)).thenReturn(mockCourses);

        when(favorRedisIntegrityService.ensureFavorCounts(any(Course.class))).thenReturn(
                new FavorCount(courseId, 1));
        when(commentRedisIntegrityService.ensureCommentCount(any(Course.class))).thenReturn(
                new CommentCount(courseId, 1));
        // Act
        CourseListResponse courseListResponse = courseService.getCourseList(placeId);

        // Assert
        assertThat(courseListResponse).isNotNull();
        assertThat(courseListResponse.getCourseFindDtos()).hasSize(1);

    }

    @DisplayName("createCourse 메서드가 유효한 인자를 넘겨받았을 때 올바른 응답을 내리는지")
    @Test
    void GivenValidInput_WhenCreateCourseTest_ThenReturnCourseCreateResponse() {
        // Arrange
        Long memberId = 1L;
        Long spotId = 1L;
        Member mockMember = new Member("shin@gmail.com", "123456", "똷", "profile.jpg", "kakao");
        Spot mockSpot = new Spot(spotId, mockMember,
                new GeometryFactory().createPoint(new Coordinate(127.123456, 37.123456)), false,
                "mockTitle", "mockDescription", new AttachFiles(new ArrayList<>()),
                new Place("mockPlaceName", "mockImageUrl",
                        new GeometryFactory().createPoint(new Coordinate(127.123456, 37.123456)),
                        "mockDescription"), 5.0);
        List<Spot> mockSpots = new ArrayList<>();
        mockSpots.add(mockSpot);
        List<Long> spotIds = new ArrayList<>();
        spotIds.add(spotId);

        String lineStringJson = "{\"type\":\"LineString\",\"coordinates\":[[127.123456,37.123456],[127.123456,37.123456]]}";
        CourseCreateRequest courseCreateRequest = new CourseCreateRequest("mockTitle", 0, spotIds,
                lineStringJson);

        when(memberService.findMemberById(memberId)).thenReturn(mockMember);
        when(spotService.getSpotListFromSpotIds(spotIds)).thenReturn(mockSpots);

        LineString mockPath = new GeometryFactory().createLineString(
                new Coordinate[]{new Coordinate(127.123456, 37.123456),
                        new Coordinate(127.123456, 37.123456)});
        Course mockCourse = new Course(mockMember, mockPath, mockSpots, 0, "mockTitle");
        when(courseRepository.save(any(Course.class))).thenReturn(mockCourse);

        // Act
        CourseCreateResponse courseCreateResponse = courseService.createCourse(memberId,
                courseCreateRequest);

        // Assert
        assertThat(courseCreateResponse).isNotNull();
        assertThat(courseCreateResponse).isInstanceOf(CourseCreateResponse.class);
        assertThat(courseCreateResponse.getMessage()).isEqualTo("경로 생성 성공");
    }


    @DisplayName("getCourseInfo 메서드가 유효한 인자를 넘겨받았을 때 올바른 응답을 내리는지")
    @Test
    void GivenValidInput_WhenGetCourseInfoTest_ThenReturnCourseFindResponse() {
        // Arrange
        Long courseId = 1L;

        Course mockCourse = new Course(
                new Member("shin@gmail.com", "123456", "똷", "profile.jpg", "kakao"),
                courseId,
                new GeometryFactory().createLineString(
                        new Coordinate[]{new Coordinate(127.123456, 37.123456),
                                new Coordinate(127.123456, 37.123456)}), new ArrayList<>(), 0,
                "mockTitle");
        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(mockCourse));

        when(commentService.getCommentList(anyLong())).thenReturn(List.of(new CommentResponse()));
        // Act
        CourseInfoResponse courseInfoResponse = courseService.getCourseInfo(courseId);

        // Assert
        assertThat(courseInfoResponse).isNotNull();
        assertThat(courseInfoResponse).isInstanceOf(CourseInfoResponse.class);
        assertThat(courseInfoResponse.getCourseId()).isEqualTo(courseId);
    }

    @DisplayName("updateCourse 메서드가 유효한 인자를 넘겨받았을 때 올바른 응답을 내리는지")
    @Test
    void GivenValidInput_WhenUpdateCourseTest_ThenReturnCourseUpdateResponse() {
        // Arrange
        Long memberId = 1L;
        Long courseId = 3L;
        Long spotId1 = 1L;
        Long spotId2 = 2L;
        List<Long> spotIds = List.of(spotId2);
        List<Long> removedSpotIds = List.of(spotId1);
        List<Long> addedSpotIds = List.of(spotId2);

        CourseUpdateRequest courseUpdateRequest = new CourseUpdateRequest(
                "mockTitle",
                "{\"type\":\"LineString\",\"coordinates\":[[127.123456,37.123456],[127.123456,37.123456]]}",
                0,
                spotIds,
                removedSpotIds,
                addedSpotIds
        );

        Member mockMember = new Member("shin@gmail.com", "123456", "똷", "profile.jpg", "kakao");
        when(memberService.findMemberById(memberId)).thenReturn(mockMember);

        Coordinate mockCoordinate1 = new Coordinate(127.123456, 37.123456);
        Coordinate mockCoordinate2 = new Coordinate(238.234567, 38.234567);
        Point mockPoint1 = new GeometryFactory().createPoint(mockCoordinate1);
        Point mockPoint2 = new GeometryFactory().createPoint(mockCoordinate2);
        Place mockPlace1 = new Place("mockPlaceName", "mockImageUrl", mockPoint1,
                "mockDescription");
        Place mockPlace2 = new Place("mockPlaceName", "mockImageUrl", mockPoint2,
                "mockDescription");
        Spot mockSpot1 = new Spot(spotId1, mockMember, mockPoint1, false,
                "mockTitle", "mockDescription", new AttachFiles(new ArrayList<>()),
                mockPlace1, 4.0);

        Spot mockSpot2 = new Spot(spotId2, mockMember, mockPoint2, false,
                "mockTitle", "mockDescription", new AttachFiles(new ArrayList<>()),
                mockPlace2, 4.5);

        when(spotRepository.findAllById(anyList())).thenReturn(List.of(mockSpot2));
        when(spotService.findSpotById(spotId1)).thenReturn(mockSpot1);
        when(spotService.findSpotById(spotId2)).thenReturn(mockSpot2);

        LineString mockLineString = new GeometryFactory().createLineString(
                new Coordinate[]{mockCoordinate1, mockCoordinate2});
        Course mockCourse = new Course(mockMember, mockLineString, new ArrayList<>(), 0,
                "mockTitle");
        when(courseRepository.save(any(Course.class))).thenReturn(mockCourse);

        // Act
        CourseUpdateResponse courseUpdateResponse = courseService.updateCourse(courseId, memberId,
                courseUpdateRequest);

        // Assert
        assertThat(courseUpdateResponse).isNotNull();
        assertThat(courseUpdateResponse.getMessage()).isEqualTo("경로 수정 성공");

    }

    @DisplayName("deleteCourse 메서드가 유효한 인자를 넘겨받았을 때 올바른 응답을 내리는지")
    @Test
    void GivenValidInput_WhenDeleteCourseTest_ThenReturnCourseDeleteResponse() {
        // Arrange
        Long memberId = 1L;
        Long courseId = 1L;

        Course mockCourse = new Course(new Member("shin@gmail.com", "123456", "똷", "profile.jpg", "kakao"), courseId, new GeometryFactory().createLineString(new Coordinate[] {new Coordinate(127.123456, 37.123456), new Coordinate(127.123456, 37.123456)}), new ArrayList<>(), 0, "mockTitle");
        when(courseRepository.findByIdAndMemberId(courseId, memberId)).thenReturn(Optional.of(mockCourse));

        // Act
        CourseDeleteResponse courseDeleteResponse = courseService.deleteCourse(courseId, memberId);

        // Assert
        assertThat(courseDeleteResponse).isNotNull();
        assertThat(courseDeleteResponse.getMessage()).isEqualTo("경로 삭제 성공");
    }

}