package kr.co.yigil.travel.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import kr.co.yigil.member.application.MemberService;
import kr.co.yigil.member.domain.Member;
import kr.co.yigil.post.application.PostService;
import kr.co.yigil.post.domain.Post;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.domain.Travel;
import kr.co.yigil.travel.domain.repository.CourseRepository;
import kr.co.yigil.travel.domain.repository.TravelRepository;
import kr.co.yigil.travel.dto.request.CourseCreateRequest;
import kr.co.yigil.travel.dto.request.CourseUpdateRequest;
import kr.co.yigil.travel.dto.response.CourseCreateResponse;
import kr.co.yigil.travel.dto.response.CourseFindResponse;
import kr.co.yigil.travel.dto.response.CourseUpdateResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @InjectMocks
    private CourseService courseService;

    @Mock
    private  MemberService memberService;

    @Mock
    private PostService postService;

    @Mock
    private SpotService spotService;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private TravelRepository travelRepository;

    @DisplayName("createCourse 메서드가 유효한 인자를 넘겨받았을 때 올바른 응답을 내리는지.")
    @Test
    void GivenValidInput_WhenCreateCourse_ThenReturnValidPostCreateReponse(){

        // Given
        Long memberId = 1L;
        String lineStringJson ="{ \"type\": \"LineString\", \"coordinates\": [[0, 0], [1, 1], [2, 2]] }";
        CourseCreateRequest courseCreateRequest = new CourseCreateRequest(
            "title1", 1, List.of(11L, 12L, 13L), lineStringJson
        );

        Member mockMember = new Member("shin@gmail.com", "123456", "God", "profile.jpg", "kakao");

        when(memberService.findMemberById(memberId)).thenReturn(mockMember);

        GeometryFactory geometryFactory = new GeometryFactory();
        Point mockPoint1 = geometryFactory.createPoint(new Coordinate(0,0));
        Spot mockSpot1 = new Spot(11L, mockPoint1, false, "anyTitle1", "아무말1","anyImageUrl1");

        Point mockPoint2 = geometryFactory.createPoint(new Coordinate(1,1));
        Spot mockSpot2 = new Spot(12L, mockPoint2, false, "anyTitle2", "아무말2","anyImageUrl2");

        Point mockPoint3 = geometryFactory.createPoint(new Coordinate(2,2));
        Spot mockSpot3 = new Spot(13L, mockPoint3, false, "anyTitle3", "아무말","anyImageUrl3");

        List<Spot> mockSpotList = List.of(mockSpot1, mockSpot2, mockSpot3);

        List<Coordinate> coordinates = List.of(
            new Coordinate(1, 1),
            new Coordinate(2, 2),
            new Coordinate(3, 3)
        );

        LineString mockPath = geometryFactory.createLineString(coordinates.toArray(new Coordinate[0]));
        Course mockCourse = new Course(mockPath, mockSpotList, 1, mockSpot1.getTitle());

        when(courseRepository.save(any())).thenReturn(mockCourse);

        doNothing().when(postService).deleteOnlyPost(Mockito.anyLong(), Mockito.anyLong());
        when(spotService .getSpotListFromSpotIds(anyList())).thenReturn(mockSpotList);

        CourseCreateResponse response = courseService.createCourse(memberId, courseCreateRequest);
        assertEquals("경로 생성 성공", response.getMessage());
    }

    @DisplayName("findCourse 메서드가 올바른 응답을 내리는지")
    @Test
    void Given_WhenfindCourse_Then(){

        Long postId = 1L;
        Member mockMember = new Member("shin@gmail.com", "123456", "God", "profile.jpg", "kakao");

        GeometryFactory geometryFactory = new GeometryFactory();
        List<Coordinate> coordinates = List.of(
            new Coordinate(1, 1),
            new Coordinate(2, 2),
            new Coordinate(3, 3)
        );
        LineString mockPath = geometryFactory.createLineString(coordinates.toArray(new Coordinate[0]));

        Point mockPoint1 = geometryFactory.createPoint(new Coordinate(0,0));
        Spot mockSpot1 = new Spot(11L, mockPoint1, false, "anyTitle1", "아무말1","anyImageUrl1");

        Point mockPoint2 = geometryFactory.createPoint(new Coordinate(1,1));
        Spot mockSpot2 = new Spot(12L, mockPoint2, false, "anyTitle2", "아무말2","anyImageUrl2");

        Point mockPoint3 = geometryFactory.createPoint(new Coordinate(2,2));
        Spot mockSpot3 = new Spot(13L, mockPoint3, false, "anyTitle3", "아무말","anyImageUrl3");

        List<Spot> mockSpotList = List.of(mockSpot1, mockSpot2, mockSpot3);

        Course mockCourse = new Course(mockPath, mockSpotList, 1, mockSpot1.getTitle());

        Post mockPost = new Post(postId, mockCourse, mockMember);

        when(postService.findPostById(anyLong())).thenReturn(mockPost);

        CourseFindResponse response =  courseService.findCourse(postId);
        assertThat(response).isInstanceOf(CourseFindResponse.class);
    }

    @DisplayName("updateCourse 메서드가 유효한 인자를 넘겨받았을 때 올바른 응답을 내리는지")
    @Test
    void GivenValidParameter_WhenUpdateCourse_ThenReturnValidResponse(){
        Long postId = 1L;
        Long memberId = 1L;
        List<Long> spotIdList = Arrays.asList(11L, 12L, 13L);
        List<Long> addedSpotIdList = Arrays.asList(14L, 15L);
        List<Long> removedSpotIdList = Arrays.asList(11L);

        GeometryFactory geometryFactory = new GeometryFactory();
        CourseUpdateRequest request = new CourseUpdateRequest(
            "title",
            "{ \"type\": \"LineString\", \"coordinates\": [[0, 0], [1, 1], [2, 2]] }",
            1,
            spotIdList,
            removedSpotIdList,
            addedSpotIdList
        );

        List<Coordinate> coordinates = List.of(
            new Coordinate(0, 0),
            new Coordinate(1, 1),
            new Coordinate(2, 2)

        );

        LineString lineString = geometryFactory.createLineString(coordinates.toArray(new Coordinate[0]));

        // spotRepository mocking
        Point mockPoint1 = geometryFactory.createPoint(new Coordinate(0,0));
        Spot mockSpot1 = new Spot(11L, mockPoint1, false, "anyTitle1", "아무말1","anyImageUrl1");

        Point mockPoint2 = geometryFactory.createPoint(new Coordinate(1,1));
        Spot mockSpot2 = new Spot(12L, mockPoint2, false, "anyTitle2", "아무말2","anyImageUrl2");

        Point mockPoint3 = geometryFactory.createPoint(new Coordinate(2,2));
        Spot mockSpot3 = new Spot(13L, mockPoint3, false, "anyTitle3", "아무말","anyImageUrl3");

        List<Spot> mockSpotList = List.of(mockSpot1, mockSpot2, mockSpot3);
        List<Travel> mockTravelList = mockSpotList.stream().map(Travel.class::cast).toList();

        when(travelRepository.findAllById(spotIdList)).thenReturn(mockTravelList);

        when(spotService.findSpotById(anyLong())).thenReturn(mockTravelList.get(0));

        Travel mockTravel = new Course(lineString, mockSpotList, 1, "");
        Member mockMember = new Member("kiit0901@gmail.com", "123456", "stone", "profile.jpg", "kakao");
        when(postService.findPostById(postId)).thenReturn(new Post(postId, mockTravel, mockMember));


        when(memberService.findMemberById(anyLong())).thenReturn(mockMember);

        LineString mockPath = geometryFactory.createLineString(coordinates.toArray(new Coordinate[0]));

        doNothing().when(postService).createPost(Mockito.any(), Mockito.any());  // createPost 메서드가 호출되면 아무런 동작도 하지 않음
        doNothing().when(postService).deleteOnlyPost(Mockito.anyLong(), Mockito.anyLong());  // deleteOnlyPost 메서드가 호출되면 아무런 동작도 하지 않음
        when(courseRepository.save(Mockito.any())).thenReturn(new Course(mockPath, mockSpotList, 1, mockSpot1.getTitle()));  // save 메서드가 호출되면 가상의 Course 객체 반환
        doNothing().when(postService).updatePost(Mockito.anyLong(), Mockito.any(), Mockito.any());  // updatePost 메서드가 호출되면 아무런 동작도 하지 않음

        assertThat(courseService.updateCourse(postId, memberId, request)).isInstanceOf(CourseUpdateResponse.class);
    }


}