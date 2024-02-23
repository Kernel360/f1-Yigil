package kr.co.yigil.travel.interfaces.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import kr.co.yigil.travel.application.CourseFacade;
import kr.co.yigil.travel.interfaces.dto.mapper.CourseMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CourseApiController.class)
@EnableSpringDataWebSupport
public class CourseApiControllerTest {

    @MockBean
    private CourseFacade courseFacade;

    @MockBean
    private CourseMapper courseMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @DisplayName("getCoursesInPLace 메서드가 잘 동작하는지")
    @Test
    void getCoursesInPlace_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/v1/courses/place/{placeId}", 1L)
                        .param("page", "0")
                        .param("size", "5")
                        .param("sortBy", "createdAt")
                        .param("sortOrder", "desc"))
                .andExpect(status().isOk());

        verify(courseFacade).getCourseSliceInPlace(anyLong(), any(Pageable.class));
    }

    @DisplayName("registerCourse 메서드가 잘 동작하는지")
    @Test
    void registerCourse_ShouldReturnOk() throws Exception {
        mockMvc.perform(post("/api/v1/courses")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());

        verify(courseFacade).registerCourse(any(), anyLong());
    }

    @DisplayName("registerCourseWithoutSeries 메서드가 잘 동작하는지")
    @Test
    void registerCourseWithoutSeries_ShouldReturnOk() throws Exception {
        mockMvc.perform(post("/api/v1/courses/only")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());

        verify(courseFacade).registerCourseWithoutSeries(any(), anyLong());
    }

    @DisplayName("retrieveCourse 메서드가 잘 동작하는지")
    @Test
    void retrieveCourse_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/v1/courses/{courseId}", 1L))
                .andExpect(status().isOk());

        verify(courseFacade).retrieveCourseInfo(anyLong());
    }

    @DisplayName("updateCourse 메서드가 잘 동작하는지")
    @Test
    void updateCourse_ShouldReturnOk() throws Exception {
        mockMvc.perform(post("/api/v1/courses/{courseId}", 1L)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());

        verify(courseFacade).modifyCourse(any(), anyLong(), anyLong());
    }
}
