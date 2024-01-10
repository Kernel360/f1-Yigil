package kr.co.yigil.travel.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import kr.co.yigil.auth.domain.Accessor;
import kr.co.yigil.travel.application.CourseService;
import kr.co.yigil.travel.dto.request.CourseCreateRequest;
import kr.co.yigil.travel.dto.request.CourseUpdateRequest;
import kr.co.yigil.travel.dto.response.CourseCreateResponse;
import kr.co.yigil.travel.dto.response.CourseFindResponse;
import kr.co.yigil.travel.dto.response.CourseUpdateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@WebMvcTest(CourseController.class)
class CourseControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    @BeforeEach
    public void setup(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @DisplayName("Course 생성 요청이 왔을 때 200 응답과 response가 잘 반환되는지")
    @Test
    void whenCreateCourse_thenReturnsOkAndCourseCreateResponse() throws Exception {
        CourseCreateResponse mockResponse = new CourseCreateResponse();
        Accessor accessor = Accessor.member(1L);

        given(courseService.createCourse(anyLong(), any(CourseCreateRequest.class))).willReturn(mockResponse);

        String jsonContent = "{"
            + "\"title\":\"Sample Title\","
            + "\"representativeSpotOrder\":1,"
            + "\"spotIds\":[1,2,3],"
            + "\"lineStringJson\":\"{\\\"type\\\": \\\"LineString\\\", \\\"coordinates\\\": [[1, 2], [3, 4]]}\""
            + "}";

        mockMvc.perform(post("/api/v1/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent)
                .sessionAttr("memberId", accessor.getMemberId()))
            .andExpect(status().isOk());
    }

    @DisplayName("Course 조회 요청이 왔을 때 200 응답과 response가 잘 반환되는지")
    @Test
    void whenFindCourse_thenReturnsOkAndCourseFindResponse() throws Exception {
        CourseFindResponse mockResponse = new CourseFindResponse();
        Long postId = 1L;

        given(courseService.findCourse(postId)).willReturn(mockResponse);

        mockMvc.perform(get("/api/v1/courses/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @DisplayName("Course 업데이트 요청이 왔을 때 200 응답과 response가 잘 반환되는지")
    @Test
    void whenUpdateCourse_thenReturnsOkAndCourseUpdateResponse() throws Exception {
        CourseUpdateResponse mockResponse = new CourseUpdateResponse();
        CourseUpdateRequest mockRequest = new CourseUpdateRequest();
        Accessor accessor = Accessor.member(1L);
        given(courseService.updateCourse(anyLong(), anyLong(), any(CourseUpdateRequest.class))).willReturn(mockResponse);

        String jsonContent = "{"
            + "\"title\":\"Updated Title\","
            + "\"representativeSpotOrder\":2,"
            + "\"spotIds\":[4,5,6],"
            + "\"lineStringJson\":\"{\\\"type\\\": \\\"LineString\\\", \\\"coordinates\\\": [[5, 6], [7, 8]]}\""
            + "}";

        mockMvc.perform(put("/api/v1/courses/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent)
                .sessionAttr("memberId", accessor.getMemberId()))
            .andExpect(status().isOk());
    }
}
