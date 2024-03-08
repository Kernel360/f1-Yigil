package kr.co.yigil.travel.course.interfaces.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import kr.co.yigil.travel.course.application.CourseFacade;
import kr.co.yigil.travel.course.domain.CourseInfoDto;
import kr.co.yigil.travel.course.interfaces.dto.CourseDto.CourseDetailResponse;
import kr.co.yigil.travel.course.interfaces.dto.CourseDto.CoursesResponse;
import kr.co.yigil.travel.course.interfaces.dto.mapper.CourseDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CourseApiController.class)
class CourseApiControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private CourseFacade courseFacade;
    @MockBean
    private CourseDtoMapper courseDtoMapper;


    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @DisplayName("코스 리스트를 조회하는 테스트")
    @Test
    void whenGetCourses_thenShouldReturn200AndCoursesResponse() throws Exception{
        when(courseFacade.getCourses(any())).thenReturn(mock(CourseInfoDto.CoursesPageInfo.class));
        when(courseDtoMapper.toPageDtp(any(CourseInfoDto.CoursesPageInfo.class))).thenReturn(
            mock(CoursesResponse.class));

        mockMvc.perform(get("/api/v1/courses"))
            .andExpect(status().isOk());

    }

    @DisplayName("코스 상세 정보를 조회하는 테스트")
    @Test
    void whenGetCourse_thenShouldReturn200AndCourseDetailResponse() throws Exception {
        when(courseFacade.getCourse(any())).thenReturn(mock(CourseInfoDto.CourseDetailInfo.class));
        when(courseDtoMapper.toDetailDto(any(CourseInfoDto.CourseDetailInfo.class))).thenReturn(mock(
            CourseDetailResponse.class));


        mockMvc.perform(get("/api/v1/courses/1"))
            .andExpect(status().isOk());
    }

    @DisplayName("코스를 삭제하는 테스트")
    @Test
    void whenDeleteCourse_thenShouldReturnCourseDeleteResponse() throws Exception{

        mockMvc.perform(get("/api/v1/courses/1"))
            .andExpect(status().isOk());
    }
}