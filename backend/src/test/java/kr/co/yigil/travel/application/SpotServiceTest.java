package kr.co.yigil.travel.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.List;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.member.domain.Member;
import kr.co.yigil.member.util.MemberUtils;
import kr.co.yigil.post.domain.Post;
import kr.co.yigil.post.domain.repository.PostRepository;
import kr.co.yigil.post.util.PostUtils;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.domain.repository.SpotRepository;
import kr.co.yigil.travel.dto.request.SpotCreateRequest;
import kr.co.yigil.travel.dto.response.SpotCreateResponse;
import kr.co.yigil.travel.dto.response.SpotFindResponse;
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
class SpotServiceTest {

    @InjectMocks
    private SpotService spotService;

    @Mock
    private SpotRepository spotRepository;
    @Mock
    private PostRepository postRepository;
    @Mock
    private PostUtils postUtils;
    @Mock
    private MemberUtils memberUtils;

    @Test
    void GivenValidInput_WhenCreateSpotTest_thenReturnValidPostId() {

//         mock request 설정
        String geoJson = "{ \"type\": \"Point\", \"coordinates\": [0, 0] }";
        SpotCreateRequest mockRequest = SpotCreateRequest.builder()
            .pointJson(geoJson)
            .isInCourse(false)
            .title("anyTitle")
            .videoUrl(null)
            .imageUrl("anyImageUrl")
            .description("아무말")
            .build();

        // mock member 설정
        Long memberId = 1L;
        Member mockMember = new Member("shin@gmail.com", "123456", "God", "profile.jpg", "kakao");
        when(memberUtils.findMemberById(memberId)).thenReturn(mockMember);

        // spotRepository mocking
        GeometryFactory geometryFactory = new GeometryFactory();
        Point mockPoint = geometryFactory.createPoint(new Coordinate(0,0));
        Spot mockSpot = new Spot(mockPoint, false, "anyTitle", "아무말","anyImageUrl", null);
        when(spotRepository.save(any(Spot.class))).thenReturn(mockSpot);

        // postRepository mocking
        Long postId = 12L;
        Post mockPost = new Post(postId, mockSpot, mockMember);
        when(postRepository.save(any(Post.class))).thenReturn(mockPost);

        // when
        SpotCreateResponse result = spotService.createSpot(memberId, mockRequest);

        // then
        assertThat(result).isEqualTo(new SpotCreateResponse(12L));
    }


    @Test
    void GivenValidPostId_WhenFindSpot_ThenReturnSpotFindResponse() {
        Long postId = 123L;
        Member mockMember = new Member("shin@gmail.com", "123456", "God", "profile.jpg", "kakao");
        GeometryFactory geometryFactory = new GeometryFactory();
        Point mockPoint = geometryFactory.createPoint(new Coordinate(0,0));
        Spot mockSpot = new Spot(mockPoint, false, "anyTitle", "아무말","anyImageUrl", null);
        Post mockPost = new Post(postId, mockSpot, mockMember);
        when(postUtils.findPostById(anyLong())).thenReturn(mockPost);

        SpotFindResponse spotFindResponse = SpotFindResponse.from(mockMember, mockSpot);

        assertThat(spotService.findSpot(postId)).isEqualTo(spotFindResponse);
    }

    @Test
    void GivenInValidPostId_WhenFindSpot_ThenThrowBadRequestException() {
        Long postId = 123L;
        Member mockMember = new Member("shin@gmail.com", "123456", "God", "profile.jpg", "kakao");

        GeometryFactory geometryFactory = new GeometryFactory();
        List<Coordinate> coordinates = List.of(
            new Coordinate(1.0, 1.0),
            new Coordinate(2.0, 2.0),
            new Coordinate(3.0, 3.0)
        );
        LineString mockLineString = geometryFactory.createLineString(coordinates.toArray(new Coordinate[0]));

        Spot mockSpot = Spot.builder().build();
        List<Spot> mockSpotList = List.of(mockSpot);
        Course mockCourse = new Course(mockLineString, mockSpotList, 2, "mock title");
        Post mockPost = new Post(postId, mockCourse, mockMember);
//        when(postRepository.findById(anyLong())).thenThrow(BadRequestException.class);
        when(postUtils.findPostById(anyLong())).thenThrow(BadRequestException.class);

        assertThrows(BadRequestException.class, ()-> spotService.findSpot(postId));
    }

    @Test
    void updateSpot() {

    }

    @Test
    void deleteSpot() {
    }

    @Test
    void findMemberByIdTest() {

    }



    @Test
    void GivenInvalidPostId_WhenFindMemberById_ThenThrowBadRequestException() {
        Long postId = 2L;
        Member mockMember = new Member("shin@gmail.com", "123456", "God", "profile.jpg", "kakao");


        when(postRepository.findById(anyLong())).thenThrow(BadRequestException.class);
        // when

//        assertThrows(BadRequestException.class, () -> spotService.findPostById(postId));
    }
}