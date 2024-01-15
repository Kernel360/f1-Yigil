package kr.co.yigil.travel.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import kr.co.yigil.comment.application.CommentService;
import kr.co.yigil.comment.domain.Comment;
import kr.co.yigil.comment.domain.repository.CommentRepository;
import kr.co.yigil.comment.dto.response.CommentResponse;
import kr.co.yigil.file.FileUploadEvent;
import kr.co.yigil.global.exception.BadRequestException;
import kr.co.yigil.global.exception.ExceptionCode;
import kr.co.yigil.member.application.MemberService;
import kr.co.yigil.member.domain.Member;
import kr.co.yigil.member.domain.SocialLoginType;
import kr.co.yigil.post.application.PostService;
import kr.co.yigil.post.domain.Post;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.Spot;
import kr.co.yigil.travel.domain.repository.SpotRepository;
import kr.co.yigil.travel.dto.request.SpotCreateRequest;
import kr.co.yigil.travel.dto.request.SpotUpdateRequest;
import kr.co.yigil.travel.dto.response.SpotCreateResponse;
import kr.co.yigil.travel.dto.response.SpotFindResponse;
import kr.co.yigil.travel.dto.response.SpotUpdateResponse;

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
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mock.web.MockMultipartFile;

@ExtendWith(MockitoExtension.class)
class SpotServiceTest {

    @InjectMocks
    private SpotService spotService;
    @Mock
    private PostService postService;
    @Mock
    private MemberService memberService;
    @Mock
    private ApplicationEventPublisher applicationEventPublisher;
    @Mock
    private SpotRepository spotRepository;
    @Mock
    private CommentService commentService;

    @DisplayName("createSpot 메서드가 유효한 인자를 넘겨받았을 때 올바른 응답을 내리는지.")
    @Test
    void GivenValidInput_WhenCreateSpotTest_thenReturnValidPostId() throws Exception {

        //         mock request 설정
        String geoJson = "{ \"type\": \"Point\", \"coordinates\": [0, 0] }";
//        MultipartFile multipartFile = new MultipartFile( );
        MockMultipartFile imageFile = new MockMultipartFile("file", "filename.jpg", "image/jpeg", new byte[10]);
        SpotCreateRequest mockRequest = new SpotCreateRequest(geoJson, "title1", "description", imageFile);

        // mock member 설정
        Long memberId = 1L;
        Member mockMember = new Member("shin@gmail.com", "123456", "God", "profile.jpg", SocialLoginType.KAKAO);
        when(memberService.findMemberById(memberId)).thenReturn(mockMember);

        // MockMvc를 사용하여 파일 업로드 시뮬레이션
        doNothing().when(applicationEventPublisher).publishEvent(any());  // publishEvent 메서드가 호출되면 아무런 동작도 하지 않음

        // when
        SpotCreateResponse response = spotService.createSpot(memberId, mockRequest);

        // 예상 결과 확인
        assertEquals("스팟 정보 생성 성공", response.getMessage());
    }

    @DisplayName("findSpot 메서드가 올바른 응답을 내리는지")
    @Test
    void GivenValidPostId_WhenfindSpotByPostId_ThenReturnSpotFindResponse() {

        Long postId = 123L;
        GeometryFactory geometryFactory = new GeometryFactory();
        Member mockMember = new Member("shin@gmail.com", "123456", "똷", "profile.jpg", "kakao");
        Point mockPoint = geometryFactory.createPoint(new Coordinate(0,0));
        Spot mockSpot = new Spot(mockPoint, false, "anyTitle", "아무말","anyImageUrl");
        Post mockPost = new Post(postId, mockSpot, mockMember);
        when(postService.findPostById(anyLong())).thenReturn(mockPost);

        //todo commentList 추가
        CommentResponse mockCommentResponse1 = new CommentResponse();
        CommentResponse mockCommentResponse2 = new CommentResponse();
        List<CommentResponse> mockCommentResponseList = List.of(mockCommentResponse1, mockCommentResponse2);
        when(commentService.getCommentList(mockSpot.getId())).thenReturn(mockCommentResponseList);
//        when(commentService.getCommentList(anyLong())).thenReturn(mockCommentResponseList);  todo 이건 왜 안되나요 ㅜㅜㅜㅜ

        SpotFindResponse spotFindResponse = SpotFindResponse.from(mockMember, mockSpot, mockCommentResponseList);

        assertThat(spotService.findSpotByPostId(postId)).isEqualTo(spotFindResponse);
    }

    @DisplayName("findSpot 메서드에서 Travel을 course로 가지고 있는 Post Id로 Spot을 찾을때 예외가 제대로 나오는지 확인")
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

        Point mockPoint = geometryFactory.createPoint(new Coordinate(0,0));
        mockPoint.setSRID(4326);

        Spot mockSpot = new Spot(mockPoint, false, "title", "www.image.com", "hello, im description");
        List<Spot> mockSpotList = List.of(mockSpot);
        Course mockCourse = new Course(mockLineString, mockSpotList, 2, "mock title");
        Post mockPost = new Post(postId, mockCourse, mockMember);
        when(postService.findPostById(anyLong())).thenReturn(mockPost);

        assertThrows(BadRequestException.class, ()-> spotService.findSpotByPostId(postId));
    }

    @DisplayName("updateSpot 메서드가 유효한 인자를 받았을 때 올바른 응답을 반환하는지")
    @Test
    void givenValidParameter_whenUpdateSpot_thenReturnSpotUpdateResponse() {
        Long memberId = 2L;
        Long postId = 2L;
        MockMultipartFile multipartFile = new MockMultipartFile(
            "file", "test.jpg", "image/jpeg", "Test".getBytes()
        );

        SpotUpdateRequest spotUpdateRequest = new SpotUpdateRequest(
            "{ \"type\": \"Point\", \"coordinates\": [0.0, 0.1] }", false, "title", "desc", multipartFile
        );

        GeometryFactory geometryFactory = new GeometryFactory();
        Point location = geometryFactory.createPoint(new Coordinate(0,0));
        location.setSRID(4326);

        Spot mockSpot = new Spot(1L, location, false, "title",  "desc", "mockUrl");
        Member mockMember = new Member("kiit0901@gmail.com", "123456", "stone", "profile.jpg", "kakao");

        when(postService.findPostById(postId)).thenReturn(new Post(postId, mockSpot, mockMember));
        when(memberService.findMemberById(memberId)).thenReturn(mockMember);
        when(spotRepository.save(any(Spot.class))).thenReturn(mockSpot);

        doAnswer(invocation -> {
            FileUploadEvent event = invocation.getArgument(0);
            event.getCallback().accept("mockUrl");
            return null;
        }).when(applicationEventPublisher).publishEvent(any(FileUploadEvent.class));

        SpotUpdateResponse response = spotService.updateSpot(memberId, postId, spotUpdateRequest);

        assertThat(response).isNotNull();
        assertThat(response.getPointJson()).isNotNull();
    }


    @DisplayName("유효한 spotId가 주어졌을 때 유효한 spot이 반환되는지")
    @Test
    void GivenValidSpotId_WhenFindSpotById_ThenReturnTavel() {

        Spot spot = new Spot(1L);
        when(spotRepository.findById(anyLong())).thenReturn(Optional.of(spot));
        assertThat(spotService.findSpotById(anyLong())).isInstanceOf(Spot.class);
    }

    @DisplayName("유효하지 않은 spotId가 주어졌을 때 예외가 발생하는지")
    @Test
    void GivenINValidSpotId_WhenFindSpotById_ThenThrowBadRequestException() {
        when(spotRepository.findById(anyLong())).thenThrow(new BadRequestException(ExceptionCode.NOT_FOUND_SPOT_ID));
        assertThrows(BadRequestException.class, () -> spotService.findSpotById(anyLong()));
    }

    @Test
    @DisplayName("getSpotListFromSpotIds 메서드 테스트")
    void testGetSpotListFromSpotIds() {
        // Given
        List<Long> spotIdList = Arrays.asList(1L, 2L, 3L);
        List<Spot> mockedSpotList = Arrays.asList(
            new Spot(1L),
            new Spot(2L),
            new Spot(3L)
        );

        // Mocking spotRepository.findById
        when(spotRepository.findById(anyLong()))
            .thenAnswer(invocation -> {
                Long spotId = invocation.getArgument(0);
                return Optional.ofNullable(mockedSpotList.stream()
                    .filter(spot -> spot.getId().equals(spotId))
                    .findFirst()
                    .orElse(null));
            });

        // Mocking castTravelToSpot
        List<Spot> resultSpotList = spotService.getSpotListFromSpotIds(spotIdList);

        // Then
        assertThat(resultSpotList).hasSize(3);
        // Add more assertions based on your logic and expected behavior
    }

}