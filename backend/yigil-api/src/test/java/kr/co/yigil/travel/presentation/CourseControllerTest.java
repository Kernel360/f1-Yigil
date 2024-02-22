package kr.co.yigil.travel.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import kr.co.yigil.travel.application.Course2Service;
import kr.co.yigil.travel.interfaces.dto.request.CourseCreateRequest;
import kr.co.yigil.travel.interfaces.dto.request.CourseUpdateRequest;
import kr.co.yigil.travel.interfaces.dto.response.CourseCreateResponse;
import kr.co.yigil.travel.interfaces.dto.response.CourseDeleteResponse;
import kr.co.yigil.travel.interfaces.dto.response.CourseInfoResponse;
import kr.co.yigil.travel.interfaces.dto.response.CourseUpdateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@WebMvcTest(CourseController.class)
public class CourseControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private CourseController courseController;

    @MockBean
    private Course2Service courseService;

    @BeforeEach
    public void setup(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @DisplayName("Course 목록 조회 요청이 왔을 때 200 응답과 response가 잘 반환되는지")
    @Test
    void GivenValidParameter_WhenGetCourseList_ThenReturnResponse200Ok() throws Exception {
        when(courseService.getCourseList(anyLong(), any(Pageable.class))).thenReturn(
            new SliceImpl<>(new ArrayList<>(), PageRequest.of(5, 5), true));

        mockMvc.perform(get("/api/v1/courses/places/1"))
            .andExpect(status().isOk());
    }

    @DisplayName("Course 생성 요청이 왔을 때 201 응답과 response가 잘 반환되는지")
    @Test
    public void createCourseTest() throws Exception {
        when(courseService.createCourse(anyLong(), any(CourseCreateRequest.class)))
            .thenReturn(new CourseCreateResponse());

        String jsonContent = "{"
            + "\"title\":\"Test Course\","
            + "\"representativeSpotOrder\":1,"
            + "\"spotIds\":[1,2,3],"
            + "\"lineStringJson\":\"{\\\"type\\\": \\\"LineString\\\", \\\"coordinates\\\": [[1, 2], [3, 4]]}\""
            + "}";

        mockMvc.perform(post("/api/v1/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent)
                .sessionAttr("memberId", 1L))
            .andExpect(status().isCreated());
    }

    @DisplayName("Course 정보 요청이 왔을 때 200 응답과 response가 잘 반환되는지")
    @Test
    public void getCourseInfoTest() throws Exception {
        when(courseService.getCourseInfo(anyLong())).thenReturn(new CourseInfoResponse());

        mockMvc.perform(get("/api/v1/courses/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @DisplayName("Course 업데이트 요청이 왔을 때 200 응답과 response가 잘 반환되는지")
    @Test
    public void updateCourseTest() throws Exception {
        when(courseService.updateCourse(anyLong(), anyLong(), any(CourseUpdateRequest.class)))
            .thenReturn(new CourseUpdateResponse());

        String jsonContent = "{"
                + "\"title\":\"Updated Title\","
                + "\"description\":\"updated Description\","
                + "\"rate\":4.5,"
                + "\"isPrivate\":false,"
                + "\"representativeSpotOrder\":2,"
                + "\"spotIds\":[4,5,6],"
                + "\"removedSpotIds\":[7,8,9],"
                + "\"addedSpotIds\":[10,11,12],"
                + "\"lineStringJson\":\"{\\\"type\\\": \\\"LineString\\\", \\\"coordinates\\\": [[5, 6], [7, 8]]}\""
                + "}";


        mockMvc.perform(
                post("/api/v1/courses/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent)
                .sessionAttr("memberId", 1L)
                ).andExpect(status().isOk());
    }

    @DisplayName("Course 삭제 요청이 왔을 때 200 응답과 response가 잘 반환되는지")
    @Test
    public void deleteCourseTest() throws Exception {
        when(courseService.deleteCourse(anyLong(), anyLong())).thenReturn(
            new CourseDeleteResponse());

        mockMvc.perform(delete("/api/v1/courses/1")
                .sessionAttr("memberId", 1L))
            .andExpect(status().isOk());
    }

}
