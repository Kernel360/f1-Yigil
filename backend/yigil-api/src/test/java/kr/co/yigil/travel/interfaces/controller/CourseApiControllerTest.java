package kr.co.yigil.travel.interfaces.controller;

import static kr.co.yigil.RestDocumentUtils.getDocumentRequest;
import static kr.co.yigil.RestDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;

import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import kr.co.yigil.travel.application.CourseFacade;
import kr.co.yigil.travel.domain.Course;
import kr.co.yigil.travel.domain.course.CourseInfo;
import kr.co.yigil.travel.interfaces.dto.CourseDetailInfoDto;
import kr.co.yigil.travel.interfaces.dto.CourseDetailInfoDto.CourseSpotInfoDto;
import kr.co.yigil.travel.interfaces.dto.CourseInfoDto;
import kr.co.yigil.travel.interfaces.dto.mapper.CourseMapper;
import kr.co.yigil.travel.interfaces.dto.response.CoursesInPlaceResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation)).build();
    }

    @DisplayName("getCoursesInPLace 메서드가 잘 동작하는지")
    @Test
    void getCoursesInPlace_ShouldReturnOk() throws Exception {
        Slice<Course> mockSlice = mock(Slice.class);
        CourseInfoDto courseInfo = new CourseInfoDto("images/static.img", "코스이름", "5.0", "3", "2024-02-01", "images/owner.jpg", "코스 작성자");
        CoursesInPlaceResponse response = new CoursesInPlaceResponse(List.of(courseInfo), false);

        when(courseFacade.getCourseSliceInPlace(anyLong(), any(Pageable.class))).thenReturn(mockSlice);
        when(courseMapper.courseSliceToCourseInPlaceResponse(mockSlice)).thenReturn(response);

        mockMvc.perform(get("/api/v1/courses/place/{placeId}", 1L)
                        .param("page", "0")
                        .param("size", "5")
                        .param("sortBy", "createdAt")
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
                                        parameterWithName("page").description("현재 페이지").optional(),
                                        parameterWithName("size").description("페이지 크기").optional(),
                                        parameterWithName("sortBy").description("정렬 옵션").optional(),
                                        parameterWithName("sortOrder").description("정렬 순서").optional()
                                ),
                                responseFields(
                                        fieldWithPath("has_next").type(JsonFieldType.BOOLEAN).description("다음 페이지가 있는지 여부"),
                                        subsectionWithPath("courses").description("course의 정보"),
                                        fieldWithPath("courses[].map_static_image_file_url").type(JsonFieldType.STRING).description("코스의 지도 정보를 나타내는 이미지 파일 경로"),
                                        fieldWithPath("courses[].title").type(JsonFieldType.STRING).description("코스의 제목"),
                                        fieldWithPath("courses[].rate").type(JsonFieldType.STRING).description("코스의 평점 정보"),
                                        fieldWithPath("courses[].spot_count").type(JsonFieldType.STRING).description("코스 내부 장소의 개수"),
                                        fieldWithPath("courses[].create_date").type(JsonFieldType.STRING).description("코스의 생성 일자"),
                                        fieldWithPath("courses[].owner_profile_image_url").type(JsonFieldType.STRING).description("코스 생성자의 프로필 이미지 경로"),
                                        fieldWithPath("courses[].owner_nickname").type(JsonFieldType.STRING).description("코스 생성자의 닉네임 정보")
                                )
                        ));

        verify(courseFacade).getCourseSliceInPlace(anyLong(), any(Pageable.class));
    }

    @DisplayName("registerCourse 메서드가 잘 동작하는지")
    @Test
    void registerCourse_ShouldReturnOk() throws Exception {
        MockMultipartFile mapStaticImage = new MockMultipartFile("mapStatic", "mapStatic.png", "image/png", "<<png data>>".getBytes());

        mockMvc.perform(multipart("/api/v1/courses")
                        .file("mapStaticImageFile", mapStaticImage.getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                        .andDo(document(
                                "courses/register-course",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                requestParts(
                                        partWithName("mapStaticImageFile").description("Course의 위치 정보를 나타내는 지도 이미지 파일")
                                ),
                                responseFields(
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답의 본문 메시지")
                                )
                        ));

        verify(courseFacade).registerCourse(any(), anyLong());
    }

    @DisplayName("registerCourseWithoutSeries 메서드가 잘 동작하는지")
    @Test
    void registerCourseWithoutSeries_ShouldReturnOk() throws Exception {
        MockMultipartFile mapStaticImage = new MockMultipartFile("mapStatic", "mapStatic.png", "image/png", "<<png data>>".getBytes());

        mockMvc.perform(multipart("/api/v1/courses/only")
                        .file("mapStaticImageFile", mapStaticImage.getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andDo(document(
                        "courses/register-course-only",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParts(
                                partWithName("mapStaticImageFile").description("Course의 위치 정보를 나타내는 지도 이미지 파일")
                        ),
                        responseFields(
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답의 본문 메시지")
                        )
                ));

        verify(courseFacade).registerCourseWithoutSeries(any(), anyLong());
    }

    @DisplayName("retrieveCourse 메서드가 잘 동작하는지")
    @Test
    void retrieveCourse_ShouldReturnOk() throws Exception {
        CourseInfo.Main mockInfo = mock(CourseInfo.Main.class);
        CourseSpotInfoDto spotInfo = new CourseSpotInfoDto("1", "장소명", List.of("images/spot.jpg", "images/spotted.png"), "4.5", "스팟 본문", "2024-02-01");
        CourseDetailInfoDto courseInfo = new CourseDetailInfoDto("최고의 코스", "4.5", "images/static.png", "코스의 본문", List.of(spotInfo));

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
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("코스의 제목"),
                                        fieldWithPath("rate").type(JsonFieldType.STRING).description("코스의 평점"),
                                        fieldWithPath("map_static_image_url").type(JsonFieldType.STRING).description("코스의 위치를 나타내는 지도 이미지 경로"),
                                        fieldWithPath("description").type(JsonFieldType.STRING).description("코스의 본문"),
                                        subsectionWithPath("spots").description("코스 내 스팟의 정보"),
                                        fieldWithPath("spots[].order").type(JsonFieldType.STRING).description("코스 내 현재 스팟의 순서"),
                                        fieldWithPath("spots[].place_name").type(JsonFieldType.STRING).description("코스 내 스팟의 장소명"),
                                        fieldWithPath("spots[].image_url_list").type(JsonFieldType.ARRAY).description("코스 내 스팟 관련 이미지의 경로 배열"),
                                        fieldWithPath("spots[].rate").type(JsonFieldType.STRING).description("코스 내 스팟의 평점 정보"),
                                        fieldWithPath("spots[].description").type(JsonFieldType.STRING).description("코스 내 스팟의 본문 정보"),
                                        fieldWithPath("spots[].create_date").type(JsonFieldType.STRING).description("코스 내 스팟의 생성 일자")
                                )
                        ));

        verify(courseFacade).retrieveCourseInfo(anyLong());
    }

    @DisplayName("updateCourse 메서드가 잘 동작하는지")
    @Test
    void updateCourse_ShouldReturnOk() throws Exception {
        mockMvc.perform(post("/api/v1/courses/{courseId}", 1L)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                        .andDo(document(
                                "courses/update-course",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                pathParameters(
                                        parameterWithName("courseId").description("코스 아이디")
                                ),
                                responseFields(
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답의 본문 메시지")
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
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답의 본문 메시지")
                        )
                ));

        verify(courseFacade).deleteCourse(anyLong(), anyLong());
    }
}