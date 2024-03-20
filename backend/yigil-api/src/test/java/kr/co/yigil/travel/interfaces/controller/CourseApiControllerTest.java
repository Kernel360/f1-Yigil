package kr.co.yigil.travel.interfaces.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.global.Selected;
import kr.co.yigil.travel.application.CourseFacade;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.course.CourseInfo;
import kr.co.yigil.travel.interfaces.dto.CourseDetailInfoDto;
import kr.co.yigil.travel.interfaces.dto.CourseDetailInfoDto.CourseSpotInfoDto;
import kr.co.yigil.travel.interfaces.dto.CourseDto;
import kr.co.yigil.travel.interfaces.dto.CourseInfoDto;
import kr.co.yigil.travel.interfaces.dto.mapper.CourseMapper;
import kr.co.yigil.travel.interfaces.dto.request.MySpotsDetailRequest;
import kr.co.yigil.travel.interfaces.dto.response.CourseSearchResponse;
import kr.co.yigil.travel.interfaces.dto.response.CoursesInPlaceResponse;
import kr.co.yigil.travel.interfaces.dto.response.MyCoursesResponse;
import kr.co.yigil.travel.interfaces.dto.response.MySpotsDetailResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.List;

import static kr.co.yigil.RestDocumentUtils.getDocumentRequest;
import static kr.co.yigil.RestDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
@WebMvcTest(CourseApiController.class)
@AutoConfigureRestDocs
public class CourseApiControllerTest {

    @MockBean
    private CourseFacade courseFacade;

    @MockBean
    private CourseMapper courseMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext,
               RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation)).build();
    }

    @DisplayName("getCoursesInPlace 메서드가 잘 동작하는지")
    @Test
    void getCoursesInPlace_ShouldReturnOk() throws Exception {
        Slice<Course> mockSlice = mock(Slice.class);
        CourseInfoDto courseInfo = new CourseInfoDto("images/static.img", "코스이름", "5.0", "3",
                "2024-02-01", "images/owner.jpg", "코스 작성자");
        CoursesInPlaceResponse response = new CoursesInPlaceResponse(List.of(courseInfo), false);

        when(courseFacade.getCourseSliceInPlace(anyLong(), any(Pageable.class))).thenReturn(
                mockSlice);
        when(courseMapper.courseSliceToCourseInPlaceResponse(mockSlice)).thenReturn(response);

        mockMvc.perform(get("/api/v1/courses/place/{placeId}", 1L)
                        .param("page", "1")
                        .param("size", "5")
                        .param("sortBy", "created_at")
                        .param("sortOrder", "desc"))
                .andExpect(status().isOk())
                .andDo(document(
                        "courses/get-courses-in-place",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("placeId").description("장소 아이디")
                        ),
                        queryParameters(
                                parameterWithName("page").description("현재 페이지 - default:1")
                                        .optional(),
                                parameterWithName("size").description("페이지 크기 - default:5")
                                        .optional(),
                                parameterWithName("sortBy").description(
                                        "정렬 옵션 - created_at(디폴트값) / rate").optional(),
                                parameterWithName("sortOrder").description(
                                        "정렬 순서 - desc(디폴트값) 내림차순 / asc 오름차순").optional()
                        ),
                        responseFields(
                                fieldWithPath("has_next").type(JsonFieldType.BOOLEAN)
                                        .description("다음 페이지가 있는지 여부"),
                                subsectionWithPath("courses").description("course의 정보"),
                                fieldWithPath("courses[].map_static_image_file_url").type(
                                                JsonFieldType.STRING)
                                        .description("코스의 지도 정보를 나타내는 이미지 파일 경로"),
                                fieldWithPath("courses[].title").type(JsonFieldType.STRING)
                                        .description("코스의 제목"),
                                fieldWithPath("courses[].rate").type(JsonFieldType.STRING)
                                        .description("코스의 평점 정보"),
                                fieldWithPath("courses[].spot_count").type(JsonFieldType.STRING)
                                        .description("코스 내부 장소의 개수"),
                                fieldWithPath("courses[].create_date").type(JsonFieldType.STRING)
                                        .description("코스의 생성 일자"),
                                fieldWithPath("courses[].owner_profile_image_url").type(
                                                JsonFieldType.STRING)
                                        .description("코스 생성자의 프로필 이미지 경로"),
                                fieldWithPath("courses[].owner_nickname").type(JsonFieldType.STRING)
                                        .description("코스 생성자의 닉네임 정보")
                        )
                ));

        verify(courseFacade).getCourseSliceInPlace(anyLong(), any(Pageable.class));
    }

    @DisplayName("registerCourse 메서드가 잘 동작하는지")
    @Test
    void registerCourse_ShouldReturnOk() throws Exception {
        MockMultipartFile mapStaticImage = new MockMultipartFile("mapStatic", "mapStatic.png",
                "image/png", "<<png data>>".getBytes());

        MockMultipartFile image1 = new MockMultipartFile("image", "image.jpg", "image/jpeg",
                "<<jpg data>>".getBytes());
        MockMultipartFile image2 = new MockMultipartFile("pic", "pic.jpg", "image/jpeg",
                "<<jpg data>>".getBytes());

        MockMultipartFile spotMapStaticImage = new MockMultipartFile("mapStatic", "mapStatic.png",
                "image/png", "<<png data>>".getBytes());
        MockMultipartFile placeImage = new MockMultipartFile("placeImg", "placeImg.png",
                "image/png", "<<png data>>".getBytes());

        String requestBody = "{\"title\" : \"test\", \"description\" : \"test\", \"rate\" : 4.5, \"isPrivate\" : false, \"representativeSpotOrder\" : 1, \"lineStringJson\" : \"test\", \"spotRegisterRequests\" : [{\"pointJson\" : \"test\", \"title\" : \"test\", \"description\" : \"test\", \"rate\" : 4.5, \"placeName\" : \"test\", \"placeAddress\" : \"test\", \"placePointJson\" : \"test\"}]}";

        mockMvc.perform(multipart("/api/v1/courses")
                        .file("mapStaticImageFile", mapStaticImage.getBytes())
                        .file("spotRegisterRequests[0].files", image1.getBytes())
                        .file("spotRegisterRequests[0].files", image2.getBytes())
                        .file("spotRegisterRequests[0].mapStaticImageFile", spotMapStaticImage.getBytes())
                        .file("spotRegisterRequests[0].placeImageFile", placeImage.getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andDo(document(
                        "courses/register-course",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParts(
                                partWithName("mapStaticImageFile").description(
                                        "Course의 위치 정보를 나타내는 지도 이미지 파일"),
                                partWithName("spotRegisterRequests[0].files").description(
                                        "스팟 관련 이미지 파일"),
                                partWithName(
                                        "spotRegisterRequests[0].mapStaticImageFile").description(
                                        "스팟의 위치 정보를 나타내는 지도 이미지 파일"),
                                partWithName("spotRegisterRequests[0].placeImageFile").description(
                                        "스팟의 장소 이미지 파일")
                        ),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING)
                                        .description("코스의 제목"),
                                fieldWithPath("description").type(JsonFieldType.STRING)
                                        .description("코스의 본문"),
                                fieldWithPath("rate").type(JsonFieldType.NUMBER)
                                        .description("코스의 평점"),
                                fieldWithPath("isPrivate").type(JsonFieldType.BOOLEAN)
                                        .description("코스의 공개 여부"),
                                fieldWithPath("representativeSpotOrder").type(JsonFieldType.NUMBER)
                                        .description("코스의 대표 스팟 순서 번호"),
                                fieldWithPath("lineStringJson").type(JsonFieldType.STRING)
                                        .description("코스의 라인 스트링 정보"),
                                subsectionWithPath("spotRegisterRequests").description(
                                        "코스 내 스팟의 정보"),
                                fieldWithPath("spotRegisterRequests[].pointJson").type(
                                        JsonFieldType.STRING).description("스팟의 포인트 정보"),
                                fieldWithPath("spotRegisterRequests[].title").type(
                                        JsonFieldType.STRING).description("스팟의 제목"),
                                fieldWithPath("spotRegisterRequests[].description").type(
                                        JsonFieldType.STRING).description("스팟의 본문"),
                                fieldWithPath("spotRegisterRequests[].rate").type(
                                        JsonFieldType.NUMBER).description("스팟의 평점"),
                                fieldWithPath("spotRegisterRequests[].placeName").type(
                                        JsonFieldType.STRING).description("스팟의 장소명"),
                                fieldWithPath("spotRegisterRequests[].placeAddress").type(
                                        JsonFieldType.STRING).description("스팟의 장소 주소"),
                                fieldWithPath("spotRegisterRequests[].placePointJson").type(
                                        JsonFieldType.STRING).description("스팟의 장소 포인트 정보")
                        ),
                        responseFields(
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("응답의 본문 메시지")
                        )
                ));

        verify(courseFacade).registerCourse(any(), anyLong());
    }

    @DisplayName("registerCourseWithoutSeries 메서드가 잘 동작하는지")
    @Test
    void registerCourseWithoutSeries_ShouldReturnOk() throws Exception {
        MockMultipartFile mapStaticImage = new MockMultipartFile("mapStatic", "mapStatic.png",
                "image/png", "<<png data>>".getBytes());

        String requestBody = "{\"title\" : \"test\", \"description\" : \"test\", \"rate\" : 4.5, \"isPrivate\" : false, \"representativeSpotOrder\" : 1, \"lineStringJson\" : \"test\", \"spotIds\" : [1, 2]}";

        mockMvc.perform(multipart("/api/v1/courses/only")
                        .file("mapStaticImageFile", mapStaticImage.getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andDo(document(
                        "courses/register-course-only",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParts(
                                partWithName("mapStaticImageFile").description(
                                        "Course의 위치 정보를 나타내는 지도 이미지 파일")
                        ),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING)
                                        .description("코스의 제목"),
                                fieldWithPath("description").type(JsonFieldType.STRING)
                                        .description("코스의 본문"),
                                fieldWithPath("rate").type(JsonFieldType.NUMBER)
                                        .description("코스의 평점"),
                                fieldWithPath("isPrivate").type(JsonFieldType.BOOLEAN)
                                        .description("코스의 공개 여부"),
                                fieldWithPath("representativeSpotOrder").type(JsonFieldType.NUMBER)
                                        .description("코스의 대표 스팟 순서 번호"),
                                fieldWithPath("lineStringJson").type(JsonFieldType.STRING)
                                        .description("코스의 라인 스트링 정보"),
                                fieldWithPath("spotIds").type(JsonFieldType.ARRAY)
                                        .description("코스 내 스팟의 아이디 배열")
                        ),
                        responseFields(
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("응답의 본문 메시지")
                        )
                ));

        verify(courseFacade).registerCourseWithoutSeries(any(), anyLong());
    }

    @DisplayName("retrieveCourse 메서드가 잘 동작하는지")
    @Test
    void retrieveCourse_ShouldReturnOk() throws Exception {
        CourseInfo.Main mockInfo = mock(CourseInfo.Main.class);
        CourseSpotInfoDto spotInfo = new CourseSpotInfoDto("1", "장소명",
                List.of("images/spot.jpg", "images/spotted.png"), "4.5", "스팟 본문", LocalDateTime.now());
        CourseDetailInfoDto courseInfo = new CourseDetailInfoDto("최고의 코스", "4.5", "images/static.png", "코스의 본문", LocalDateTime.now(), List.of(spotInfo));


        when(courseFacade.retrieveCourseInfo(anyLong())).thenReturn(mockInfo);
        when(courseMapper.toCourseDetailInfoDto(mockInfo)).thenReturn(courseInfo);

        mockMvc.perform(get("/api/v1/courses/{courseId}", 1L))
                .andExpect(status().isOk())
                .andDo(document(
                        "courses/retrieve-course",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("courseId").description("코스 아이디")
                        ),
                        responseFields(
                                fieldWithPath("title").type(JsonFieldType.STRING)
                                        .description("코스의 제목"),
                                fieldWithPath("rate").type(JsonFieldType.STRING)
                                        .description("코스의 평점"),
                                fieldWithPath("map_static_image_url").type(JsonFieldType.STRING)
                                        .description("코스의 위치를 나타내는 지도 이미지 경로"),
                                fieldWithPath("description").type(JsonFieldType.STRING)
                                        .description("코스의 본문"),
                                fieldWithPath("created_date").type(JsonFieldType.STRING)
                                        .description("코스의 생성 일자"),
                                subsectionWithPath("spots").description("코스 내 스팟의 정보"),
                                fieldWithPath("spots[].order").type(JsonFieldType.STRING)
                                        .description("코스 내 현재 스팟의 순서"),
                                fieldWithPath("spots[].place_name").type(JsonFieldType.STRING)
                                        .description("코스 내 스팟의 장소명"),
                                fieldWithPath("spots[].image_url_list").type(JsonFieldType.ARRAY)
                                        .description("코스 내 스팟 관련 이미지의 경로 배열"),
                                fieldWithPath("spots[].rate").type(JsonFieldType.STRING)
                                        .description("코스 내 스팟의 평점 정보"),
                                fieldWithPath("spots[].description").type(JsonFieldType.STRING)
                                        .description("코스 내 스팟의 본문 정보"),
                                fieldWithPath("spots[].create_date").type(JsonFieldType.STRING)
                                        .description("코스 내 스팟의 생성 일자")
                        )
                ));

        verify(courseFacade).retrieveCourseInfo(anyLong());
    }

    @DisplayName("updateCourse 메서드가 잘 동작하는지")
    @Test
    void updateCourse_ShouldReturnOk() throws Exception {
        MockMultipartFile image1 = new MockMultipartFile("image", "image.jpg", "image/jpeg",
                "<<jpg data>>".getBytes());

        String requestBody = "{\"description\" : \"test\", \"rate\" : 4.5, \"spotIdOrder\" : [1, 2], \"courseSpotUpdateRequests\" : [{\"id\" : 1, \"description\" : \"test\", \"rate\" : 4.5, \"originalSpotImages\" : [{\"imageUrl\" : \"images/spot.jpg\", \"index\" : 1}], \"updateSpotImages\" : [{\"index\" : 1}]}]}";

        mockMvc.perform(multipart("/api/v1/courses/{courseId}", 1L)
                        .file("courseSpotUpdateRequests[0].updateSpotImages[0].imageFile",
                                image1.getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andDo(document(
                        "courses/update-course",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("courseId").description("코스 아이디")
                        ),
                        requestParts(
                                partWithName(
                                        "courseSpotUpdateRequests[0].updateSpotImages[0].imageFile").description(
                                        "스팟 관련 이미지 파일")
                        ),
                        requestFields(
                                fieldWithPath("description").type(JsonFieldType.STRING)
                                        .description("코스의 본문"),
                                fieldWithPath("rate").type(JsonFieldType.NUMBER)
                                        .description("코스의 평점"),
                                fieldWithPath("spotIdOrder").type(JsonFieldType.ARRAY)
                                        .description("코스 내 스팟의 아이디 배열"),
                                subsectionWithPath("courseSpotUpdateRequests").description(
                                        "코스 내 스팟의 정보"),
                                fieldWithPath("courseSpotUpdateRequests[].id").type(
                                        JsonFieldType.NUMBER).description("스팟의 아이디"),
                                fieldWithPath("courseSpotUpdateRequests[].description").type(
                                        JsonFieldType.STRING).description("스팟의 본문"),
                                fieldWithPath("courseSpotUpdateRequests[].rate").type(
                                        JsonFieldType.NUMBER).description("스팟의 평점"),
                                fieldWithPath("courseSpotUpdateRequests[].originalSpotImages").type(
                                        JsonFieldType.ARRAY).description("스팟의 기존 이미지 정보"),
                                fieldWithPath("courseSpotUpdateRequests[].updateSpotImages").type(
                                        JsonFieldType.ARRAY).description("스팟의 업데이트 이미지 정보"),
                                fieldWithPath(
                                        "courseSpotUpdateRequests[].originalSpotImages[].imageUrl").type(
                                        JsonFieldType.STRING).description("스팟의 기존 이미지 경로"),
                                fieldWithPath(
                                        "courseSpotUpdateRequests[].originalSpotImages[].index").type(
                                        JsonFieldType.NUMBER).description("스팟의 기존 이미지 인덱스"),
                                fieldWithPath(
                                        "courseSpotUpdateRequests[].updateSpotImages[].index").type(
                                        JsonFieldType.NUMBER).description("스팟의 업데이트 이미지 인덱스")
                        ),
                        responseFields(
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("응답의 본문 메시지")
                        )
                ));

        verify(courseFacade).modifyCourse(any(), anyLong(), anyLong());
    }

    @DisplayName("deleteCourse 메서드가 잘 동작하는지")
    @Test
    void deleteCourse_ShouldReturnOk() throws Exception {
        mockMvc.perform(delete("/api/v1/courses/{courseId}", 1L))
                .andExpect(status().isOk())
                .andDo(document(
                        "courses/delete-course",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("courseId").description("코스 아이디")
                        ),
                        responseFields(
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("응답의 본문 메시지")
                        )
                ));

        verify(courseFacade).deleteCourse(anyLong(), anyLong());
    }


    @DisplayName("내가 작성한 코스 목록 조회가 잘 되는지")
    @Test
    void getMyCourseInfo_ShouldReturnOk() throws Exception {

        MyCoursesResponse.CourseInfo courseInfo = MyCoursesResponse.CourseInfo.builder()
                .courseId(1L)
                .title("test course")
                .rate(4.5)
                .spotCount(10)
                .createdDate("2024-01-01")
                .mapStaticImageUrl("images/map.jpg")
                .isPrivate(false)
                .build();

        MyCoursesResponse response = MyCoursesResponse.builder()
                .content(List.of(courseInfo))
                .totalPages(1)
                .build();

        when(courseFacade.getMemberCoursesInfo(anyLong(), any(PageRequest.class),
                any(Selected.class))).thenReturn(mock(CourseInfo.MyCoursesResponse.class));
        when(courseMapper.of(any(CourseInfo.MyCoursesResponse.class))).thenReturn(response);

        mockMvc.perform(get("/api/v1/courses/my")
                        .param("page", "1")
                        .param("size", "5")
                        .param("sortBy", "created_at")
                        .param("sortOrder", "desc")
                        .param("selected", "public")
                )
                .andExpect(status().isOk())
                .andDo(document(
                        "courses/get-my-course-list",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        queryParameters(
                                parameterWithName("page").description("현재 페이지 - default:1")
                                        .optional(),
                                parameterWithName("size").description("페이지 크기 - default:5")
                                        .optional(),
                                parameterWithName("sortBy").description(
                                        "정렬 옵션 - createdAt(디폴트값) / rate").optional(),
                                parameterWithName("sortOrder").description(
                                        "정렬 순서 - desc(디폴트값) 내림차순 / asc 오름차순").optional(),
                                parameterWithName("selected").description(
                                        "필터 기능 - all(디폴트값) 전체공개 / private 비공개").optional()
                        ),
                        responseFields(
                                fieldWithPath("content[].course_id").description("코스 ID"),
                                fieldWithPath("content[].title").description("코스 제목"),
                                fieldWithPath("content[].rate").description("코스 평점"),
                                fieldWithPath("content[].spot_count").description("코스 포함 장소 수"),
                                fieldWithPath("content[].created_date").description("코스 생성일"),
                                fieldWithPath("content[].map_static_image_url").description(
                                        "코스 지도 이미지 URL"),
                                fieldWithPath("content[].is_private").description("공개여부"),
                                fieldWithPath("total_pages").description("총 페이지 수")
                        )
                ));
        verify(courseFacade).getMemberCoursesInfo(anyLong(), any(PageRequest.class),
                any(Selected.class));
    }

    @DisplayName("searchCourseByPlaceName 메서드가 잘 동작하는지")
    @Test
    void searchCourseByPlaceName_ShouldReturnOk() throws Exception {
        String keyword = "keyword";
        CourseInfo.Slice mockSlice = mock(CourseInfo.Slice.class);
        CourseDto dto = new CourseDto(1L, "title", "mapStatic.jpg", "profile.png", "nickname", 3, 4.5, false,
                LocalDateTime.now());
        CourseSearchResponse response = new CourseSearchResponse(List.of(dto), false);

        when(courseFacade.searchCourseByPlaceName(eq(keyword), any(Accessor.class), any(Pageable.class))).thenReturn(mockSlice);
        when(courseMapper.toCourseSearchResponse(mockSlice)).thenReturn(response);

        mockMvc.perform(get("/api/v1/courses/search")
                        .param("keyword", keyword)
                        .param("page", "1")
                        .param("size", "5")
                        .param("sortBy", "created_at")
                        .param("sortOrder", "desc")
                )
                .andExpect(status().isOk())
                .andDo(document(
                        "courses/search-course-by-place-name",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        queryParameters(
                                parameterWithName("keyword").description("검색 키워드"),
                                parameterWithName("page").description("현재 페이지 - default:1")
                                        .optional(),
                                parameterWithName("size").description("페이지 크기 - default:5")
                                        .optional(),
                                parameterWithName("sortBy").description(
                                        "정렬 옵션 - created_at(디폴트값) / rate / name").optional(),
                                parameterWithName("sortOrder").description(
                                        "정렬 순서 - desc(디폴트값) 내림차순 / asc 오름차순").optional()
                        ),
                        responseFields(
                                fieldWithPath("has_next").type(JsonFieldType.BOOLEAN).description("다음 페이지가 있는지 여부"),
                                subsectionWithPath("courses").description("course의 정보"),
                                fieldWithPath("courses[].id").type(JsonFieldType.NUMBER).description("코스 ID"),
                                fieldWithPath("courses[].title").type(JsonFieldType.STRING).description("코스 제목"),
                                fieldWithPath("courses[].map_static_image_url").type(JsonFieldType.STRING).description(
                                        "코스 지도 이미지 URL"),
                                fieldWithPath("courses[].owner_profile_image_url").type(JsonFieldType.STRING).description(
                                        "코스 작성자 프로필 이미지 URL"),
                                fieldWithPath("courses[].owner_nickname").type(JsonFieldType.STRING).description("코스 작성자 닉네임"),
                                fieldWithPath("courses[].spot_count").type(JsonFieldType.NUMBER).description("코스 포함 장소 수"),
                                fieldWithPath("courses[].rate").type(JsonFieldType.NUMBER).description("코스 평점"),
                                fieldWithPath("courses[].liked").type(JsonFieldType.BOOLEAN).description("코스 좋아요 여부"),
                                fieldWithPath("courses[].create_date").type(JsonFieldType.STRING).description("코스 생성일")
                        )
                ));
    }

    @DisplayName("getMySpotsDetailInfo 메서드가 잘 동작하는지")
    @Test
    void whenGetMySpotsDetailInfo_thenShouldReturn200AndResponse() throws Exception {

        MySpotsDetailRequest request = new MySpotsDetailRequest();
        request.setSpotIds(List.of(1L, 2L));

        MySpotsDetailResponse.SpotDetailDto spotDetailDto1 = MySpotsDetailResponse.SpotDetailDto.builder()
                .spotId(1L)
                .placeName("장소명")
                .placeAddress("장소주소")
                .rate(4.5)
                .description("스팟 본문")
                .imageUrls(List.of("images/spot.jpg", "images/spotted.png"))
                .createDate(LocalDateTime.now())
                .point(MySpotsDetailResponse.PointDto.builder().x(1.0).y(1.0).build())
                .build();

        MySpotsDetailResponse.SpotDetailDto spotDetailDto2 = MySpotsDetailResponse.SpotDetailDto.builder()
                .spotId(2L)
                .placeName("장소명")
                .placeAddress("장소주소")
                .rate(4.5)
                .description("스팟 본문")
                .imageUrls(List.of("images/spot.jpg", "images/spotted.png"))
                .createDate(LocalDateTime.now())
                .point(MySpotsDetailResponse.PointDto.builder().x(2.0).y(2.0).build())
                .build();

        MySpotsDetailResponse response = new MySpotsDetailResponse();
        response.setSpotDetails(List.of(spotDetailDto1, spotDetailDto2));

        when(courseFacade.getMySpotsDetailInfo(anyList(), anyLong())).thenReturn(mock(CourseInfo.MySpotsInfo.class));

        when(courseMapper.toMySpotsDetailResponse(any(CourseInfo.MySpotsInfo.class))).thenReturn(response);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/v1/courses/spots")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(document(
                        "courses/get-my-spots-detail-info",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("spot_ids").type(JsonFieldType.ARRAY).description("스팟 아이디 배열")
                        ),
                        responseFields(
                                subsectionWithPath("spot_details").description("스팟의 상세 정보"),
                                fieldWithPath("spot_details[].spot_id").type(JsonFieldType.NUMBER).description("스팟 아이디"),
                                fieldWithPath("spot_details[].place_name").type(JsonFieldType.STRING).description("장소명"),
                                fieldWithPath("spot_details[].place_address").type(JsonFieldType.STRING).description("장소주소"),
                                fieldWithPath("spot_details[].rate").type(JsonFieldType.NUMBER).description("평점"),
                                fieldWithPath("spot_details[].description").type(JsonFieldType.STRING).description("스팟 본문"),
                                fieldWithPath("spot_details[].image_urls").type(JsonFieldType.ARRAY).description("이미지 URL 배열"),
                                fieldWithPath("spot_details[].create_date").type(JsonFieldType.STRING).description("생성일"),
                                subsectionWithPath("spot_details[].point").description("스팟의 좌표 정보")
                        )
                ));

    }
}